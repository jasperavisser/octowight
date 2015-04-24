package nl.haploid.octowight.registry.data;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.repository.ResourceDmo;
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

	public ResourceRoot fromResourceDmo(final ResourceDmo resourceDmo) {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setAtomId(resourceDmo.getAtomId());
		resourceRoot.setAtomLocus(resourceDmo.getAtomLocus());
		resourceRoot.setAtomType(resourceDmo.getAtomType());
		resourceRoot.setResourceId(resourceDmo.getResourceId());
		resourceRoot.setResourceType(resourceDmo.getResourceType());
		return resourceRoot;
	}
}
