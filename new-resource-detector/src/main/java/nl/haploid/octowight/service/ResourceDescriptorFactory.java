package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;

public class ResourceDescriptorFactory {

	// TODO: test
	public static ResourceDescriptor fromAtomChangeEvent(final AtomChangeEvent event, final String resourceType) {
		final ResourceDescriptor descriptor = new ResourceDescriptor();
		descriptor.setAtomId(event.getAtomId());
		descriptor.setAtomLocus(event.getAtomLocus());
		descriptor.setAtomType(event.getAtomType());
		descriptor.setResourceType(resourceType);
		return descriptor;
	}
}
