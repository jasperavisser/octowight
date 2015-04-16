package nl.haploid.octowight.service;

import nl.haploid.octowight.data.Resource;
import nl.haploid.octowight.data.ResourceFactory;
import nl.haploid.octowight.repository.ResourceDmo;
import nl.haploid.octowight.repository.ResourceDmoFactory;
import nl.haploid.octowight.repository.ResourceDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private ResourceFactory resourceFactory;

	@Autowired
	private ResourceDmoFactory resourceDmoFactory;

	public boolean isNewResource(final Resource resource) {
		final ResourceDmo dmo = resourceRepository.findByAtomIdAndAtomTypeAndAtomLocus(resource.getAtomId(), resource.getAtomType(), resource.getAtomLocus());
		return dmo == null;
	}

	public Resource saveResource(final Resource resource) {
		final ResourceDmo dmo = resourceRepository.saveAndFlush(resourceDmoFactory.fromResource(resource));
		return resourceFactory.fromResourceDmo(dmo);
	}
}
