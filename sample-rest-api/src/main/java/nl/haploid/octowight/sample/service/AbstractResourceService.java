package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.*;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.data.ResourceFactory;
import nl.haploid.octowight.sample.data.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

// TODO: separate library for generic API stuff
public abstract class AbstractResourceService<M extends Model, R extends Resource<M>> {

	@Autowired
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Autowired
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Autowired
	private ResourceElementDmoFactory resourceElementDmoFactory;

	@Autowired
	private ResourceModelDmoFactory resourceModelDmoFactory;

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
		final ResourceRoot resourceRoot = getResourceRoot(getResourceType(), resourceId);
		final R resource = resourceFactory.fromResourceRoot(resourceRoot);
		final M cachedModel = getCachedModel(resource);
		if (cachedModel != null) {
			return cachedModel;
		}
		saveResourceElements(resource);
		final M model = resource.getModel();
		saveModel(resource, model);
		return model;
	}

	// TODO: test
	protected M getCachedModel(final R resource) {
		final ResourceModelDmo modelDmo = resourceModelDmoRepository.findByResourceTypeAndResourceId(resource.getType(), resource.getId());
		if (modelDmo != null && modelDmo.getVersion().equals(resource.getVersion())) {
			return modelSerializer.deserialize(modelDmo.getBody(), getModelClass());
		}
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
		final ResourceModelDmo resourceModelDmo = resourceModelDmoRepository.findByResourceTypeAndResourceId(resource.getType(), resource.getId());
		if (resourceModelDmo != null) {
			resourceModelDmo.setBody(body);
			resourceModelDmo.setVersion(resource.getVersion());
			return resourceModelDmo;
		}
		return resourceModelDmoFactory.fromResourceAndBody(resource, body);
	}

	// TODO: test
	protected void saveModel(final R resource, final M model) {
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
