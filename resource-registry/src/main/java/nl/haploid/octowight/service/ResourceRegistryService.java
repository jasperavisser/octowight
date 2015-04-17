package nl.haploid.octowight.service;

import nl.haploid.octowight.data.Resource;
import nl.haploid.octowight.data.ResourceFactory;
import nl.haploid.octowight.repository.ResourceDmo;
import nl.haploid.octowight.repository.ResourceDmoFactory;
import nl.haploid.octowight.repository.ResourceDmoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private ResourceFactory resourceFactory;

	@Autowired
	private ResourceDmoFactory resourceDmoFactory;

	public boolean isNewResource(final Resource resource) {
		final ResourceDmo dmo = resourceRepository
				.findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus(resource.getResourceType(),
						resource.getAtomId(), resource.getAtomType(), resource.getAtomLocus());
		return dmo == null;
	}

	public Resource saveResource(final Resource resource) {
		final ResourceDmo resourceDmo = resourceDmoFactory.fromResource(resource);
		final ResourceDmo dmo = resourceRepository.saveAndFlush(resourceDmo);
		log.debug(String.format("Saved resource: %s/%d", dmo.getResourceType(), dmo.getResourceId()));
		return resourceFactory.fromResourceDmo(dmo);
	}
}
