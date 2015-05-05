package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.*;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.data.ResourceFactory;
import nl.haploid.octowight.sample.data.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// TODO: separate library for generic API stuff
public abstract class AbstractResourceService<M extends Model, R extends Resource<M>> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Autowired
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Autowired
	private ResourceElementDmoFactory resourceElementDmoFactory;

	@Autowired
	private ResourceModelDocumentFactory resourceModelDocumentFactory;

	@Autowired
	private ResourceModelDmoRepository resourceModelDmoRepository;

	@Autowired
	private ModelSerializer<M> modelSerializer;

	@Autowired
	private ResourceFactory<R> resourceFactory;

	public abstract String getResourceType();

	// TODO: check if it is still a resource (captain resource detector)
	// TODO: test
	@Transactional("registryTransactionManager")
	public M getModel(final long resourceId) {
		log.debug(String.format("Get model for resource %s/%d", getResourceType(), resourceId));
		final ResourceRoot resourceRoot = getResourceRoot(getResourceType(), resourceId);
		// TODO: fromResourceRoot will fetch data, even though we're using cached model later
		final R resource = resourceFactory.fromResourceRoot(resourceRoot);
		final M cachedModel = getCachedModel(resource);
		if (cachedModel != null) {
			log.debug(String.format("Using cached model for resource %s/%d", getResourceType(), resourceId));
			return cachedModel;
		}
		saveResourceElements(resource);
		final M model = resource.getModel();
		saveModel(resource, model);
		return model;
	}

	// TODO: test
	protected M getCachedModel(final R resource) {
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resource.getId());
		resourceModelId.setResourceType(resource.getType());
		final ResourceModelDmo resourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId);
		if (resourceModelDmo != null && resourceModelDmo.getVersion().equals(resource.getVersion())) {
			return modelSerializer.deserialize(resourceModelDmo.getBody(), getModelClass());
		}
		// TODO: factory method
		return null;
	}

	protected abstract Class<M> getModelClass();

	protected ResourceRoot getResourceRoot(final String resourceType, final long resourceId) {
		final ResourceRootDmo resourceRootDmo = resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId);
		if (resourceRootDmo == null) {
			throw new ResourceNotFoundException();
		}
		return resourceRootFactory.fromResourceDmo(resourceRootDmo);
	}

	private ResourceModelDmo createModelDmo(final R resource, final M model) {
		final String body = modelSerializer.serialize(model);
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resource.getId());
		resourceModelId.setResourceType(resource.getType());
		final ResourceModelDmo resourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId);
		if (resourceModelDmo != null) {
			resourceModelDmo.setBody(body);
			resourceModelDmo.setVersion(resource.getVersion());
			return resourceModelDmo;
		}
		return resourceModelDocumentFactory.fromResourceAndBody(resource, body);
	}

	// TODO: test
	protected void saveModel(final R resource, final M model) {
		log.debug(String.format("Save model for resource %s/%d", resource.getType(), resource.getId()));
		final ResourceModelDmo resourceModelDmo = createModelDmo(resource, model);
		resourceModelDmoRepository.save(resourceModelDmo);
	}

	// TODO: test
	protected void saveResourceElements(final R resource) {
		resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType(), resource.getId());
		resourceElementDmoRepository.flush();
		resource.getAtoms().stream()
				.forEach(atom -> {
					final ResourceElementDmo dmo = resourceElementDmoFactory.fromResourceAndAtom(resource, atom);
					resourceElementDmoRepository.save(dmo);
				});
	}
}
