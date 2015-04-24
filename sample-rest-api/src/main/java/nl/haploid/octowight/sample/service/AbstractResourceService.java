package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.registry.data.*;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.data.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public abstract class AbstractResourceService<T extends Model> {

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
    private ModelSerializer<T> modelSerializer;

    protected ModelSerializer<T> getModelSerializer() {
        return modelSerializer;
    }

    protected ResourceRoot getResourceRoot(final String resourceType, final long resourceId) {
        final ResourceRootDmo resourceRootDmo = resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId);
        if (resourceRootDmo == null) {
            throw new ResourceNotFoundException();
        }
        return resourceRootFactory.fromResourceDmo(resourceRootDmo);
    }

    private ResourceModelDmo createModel(final Resource<T> resource, final String body) {
        final ResourceModelDmo resourceModelDmo = resourceModelDmoRepository.findByResourceTypeAndResourceId(resource.getType(), resource.getId());
        if (resourceModelDmo != null) {
            resourceModelDmo.setBody(body);
            resourceModelDmo.setVersion(resource.getVersion());
            return resourceModelDmo;
        }
        return resourceModelDmoFactory.fromResourceAndBody(resource, body);
    }

    // TODO: test
    protected void saveModel(final Resource<T> resource, final String body) {
        final ResourceModelDmo resourceModelDmo = createModel(resource, body);
        resourceModelDmoRepository.save(resourceModelDmo);
    }

    // TODO: test
    protected void saveResourceElements(final Resource resource) {
        resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType(), resource.getId());
        resourceElementDmoRepository.flush();
        final Collection<Atom> atoms = resource.getAtoms();
        atoms.stream()
                .forEach(atom -> saveResourceElement(resource, atom));
    }

    // TODO: test
    protected void saveResourceElement(final Resource resource, final Atom atom) {
        final ResourceElementDmo dmo = resourceElementDmoFactory.fromResourceAndAtom(resource, atom);
        resourceElementDmoRepository.save(dmo);
    }
}
