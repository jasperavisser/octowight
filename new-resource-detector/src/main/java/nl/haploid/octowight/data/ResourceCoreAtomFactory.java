package nl.haploid.octowight.data;

import nl.haploid.octowight.AtomChangeEvent;

public class ResourceCoreAtomFactory {

	// TODO: test
	public static ResourceCoreAtom fromAtomChangeEvent(final AtomChangeEvent event, final String resourceType) {
		final ResourceCoreAtom coreAtom = new ResourceCoreAtom();
		coreAtom.setAtomId(event.getAtomId());
		coreAtom.setAtomLocus(event.getAtomLocus());
		coreAtom.setAtomType(event.getAtomType());
		coreAtom.setResourceType(resourceType);
		return coreAtom;
	}
}
