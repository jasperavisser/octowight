package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.ResourceRoot;
import org.springframework.stereotype.Component;

@Component
public class ResourceRootDmoFactory {

	public ResourceRootDmo fromResourceRoot(final ResourceRoot resourceRoot) {
		final ResourceRootDmo resourceRootDmo = new ResourceRootDmo();
		resourceRootDmo.setAtomId(resourceRoot.getAtomId());
		resourceRootDmo.setAtomLocus(resourceRoot.getAtomLocus());
		resourceRootDmo.setAtomType(resourceRoot.getAtomType());
		resourceRootDmo.setResourceId(resourceRoot.getResourceId());
		resourceRootDmo.setResourceType(resourceRoot.getResourceType());
		return resourceRootDmo;
	}
}
