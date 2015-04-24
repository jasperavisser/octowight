package nl.haploid.octowight.registry.service;

import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoFactory;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService { // TODO: rename?

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private ResourceRootFactory resourceRootFactory;

	@Autowired
	private ResourceDmoFactory resourceDmoFactory;

	public boolean isNewResource(final ResourceRoot resourceRoot) {
		final ResourceDmo dmo = resourceRepository
				.findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(resourceRoot.getResourceType(),
						resourceRoot.getAtomId(), resourceRoot.getAtomType(), resourceRoot.getAtomLocus());
		return dmo == null;
	}

	public ResourceRoot saveResource(final ResourceRoot resourceRoot) {
		final ResourceDmo resourceDmo = resourceDmoFactory.fromResource(resourceRoot);
		final ResourceDmo dmo = resourceRepository.saveAndFlush(resourceDmo);
		log.debug(String.format("Saved resource: %s/%d", dmo.getResourceType(), dmo.getResourceId()));
		return resourceRootFactory.fromResourceDmo(dmo);
	}
}
