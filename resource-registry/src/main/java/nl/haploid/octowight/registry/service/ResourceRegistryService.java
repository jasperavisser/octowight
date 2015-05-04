package nl.haploid.octowight.registry.service;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import nl.haploid.octowight.registry.repository.ResourceRootDmoFactory;
import nl.haploid.octowight.registry.repository.ResourceRootDmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ResourceRootDmoRepository resourceRepository;

    @Autowired
    private ResourceRootFactory resourceRootFactory;

    @Autowired
    private ResourceRootDmoFactory resourceRootDmoFactory;

    public boolean isNewResource(final ResourceRoot resourceRoot) {
        final ResourceRootDmo dmo = resourceRepository
                .findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(resourceRoot.getResourceType(),
                        resourceRoot.getAtomId(), resourceRoot.getAtomType(), resourceRoot.getAtomLocus());
        return dmo == null;
    }

    public ResourceRoot saveResource(final ResourceRoot resourceRoot) {
        final ResourceRootDmo resourceRootDmo = resourceRootDmoFactory.fromResourceRoot(resourceRoot);
        final ResourceRootDmo dmo = resourceRepository.saveAndFlush(resourceRootDmo);
        log.debug(String.format("Saved resource: %s/%d", dmo.getResourceType(), dmo.getResourceId()));
        return resourceRootFactory.fromResourceDmo(dmo);
    }
}
