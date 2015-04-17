package nl.haploid.octowight.registry.data;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import org.springframework.stereotype.Component;

@Component
public class ResourceFactory {

	public Resource fromAtomChangeEvent(final AtomChangeEvent event, final String resourceType) {
		final Resource resource = new Resource();
		resource.setAtomId(event.getAtomId());
		resource.setAtomLocus(event.getAtomLocus());
		resource.setAtomType(event.getAtomType());
		resource.setResourceType(resourceType);
		return resource;
	}

	public Resource fromResourceDmo(final ResourceDmo resourceDmo) {
		final Resource resource = new Resource();
		resource.setAtomId(resourceDmo.getAtomId());
		resource.setAtomLocus(resourceDmo.getAtomLocus());
		resource.setAtomType(resourceDmo.getAtomType());
		resource.setResourceId(resourceDmo.getResourceId());
		resource.setResourceType(resourceDmo.getResourceType());
		return resource;
	}
}
