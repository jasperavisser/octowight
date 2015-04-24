package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.ResourceRoot;
import org.springframework.stereotype.Component;

@Component
public class ResourceDmoFactory {

	public ResourceDmo fromResource(final ResourceRoot resourceRoot) {
		final ResourceDmo resourceDmo = new ResourceDmo();
		resourceDmo.setAtomId(resourceRoot.getAtomId());
		resourceDmo.setAtomLocus(resourceRoot.getAtomLocus());
		resourceDmo.setAtomType(resourceRoot.getAtomType());
		resourceDmo.setResourceId(resourceRoot.getResourceId());
		resourceDmo.setResourceType(resourceRoot.getResourceType());
		return resourceDmo;
	}
}
