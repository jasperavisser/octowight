package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceDmoFactory {

	public ResourceDmo fromResource(final Resource resource) {
		final ResourceDmo resourceDmo = new ResourceDmo();
		resourceDmo.setAtomId(resource.getAtomId());
		resourceDmo.setAtomLocus(resource.getAtomLocus());
		resourceDmo.setAtomType(resource.getAtomType());
		resourceDmo.setResourceId(resource.getResourceId());
		resourceDmo.setResourceType(resource.getResourceType());
		return resourceDmo;
	}
}
