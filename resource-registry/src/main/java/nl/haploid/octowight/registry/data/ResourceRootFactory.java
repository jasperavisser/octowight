package nl.haploid.octowight.registry.data;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import org.springframework.stereotype.Component;

@Component
public class ResourceRootFactory {

	public ResourceRoot fromAtomChangeEvent(final AtomChangeEvent event, final String resourceType) {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setAtomId(event.getAtomId());
		resourceRoot.setAtomLocus(event.getAtomLocus());
		resourceRoot.setAtomType(event.getAtomType());
		resourceRoot.setResourceType(resourceType);
		return resourceRoot;
	}

	public ResourceRoot fromResourceRootDmo(final ResourceRootDmo resourceRootDmo) {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setAtomId(resourceRootDmo.getAtomId());
		resourceRoot.setAtomLocus(resourceRootDmo.getAtomLocus());
		resourceRoot.setAtomType(resourceRootDmo.getAtomType());
		resourceRoot.setResourceId(resourceRootDmo.getResourceId());
		resourceRoot.setResourceType(resourceRootDmo.getResourceType());
		resourceRoot.setVersion(resourceRootDmo.getVersion());
		return resourceRoot;
	}
}
