package nl.haploid.octowight;

import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.ResourceCoreAtomDmo;

import java.util.Random;

public class TestData {

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static ResourceCoreAtom resourceCoreAtom(final Long resourceId) {
		final ResourceCoreAtom coreAtom = new ResourceCoreAtom();
		coreAtom.setResourceId(resourceId);
		coreAtom.setResourceType("olson");
		coreAtom.setAtomId(nextLong());
		coreAtom.setAtomLocus("madison avenue");
		coreAtom.setAtomType("draper");
		return coreAtom;
	}

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomLocus("everywhere");
		event.setAtomType(atomType);
		return event;
	}

	public static ResourceCoreAtomDmo resourceCoreAtomDmo() {
		final ResourceCoreAtomDmo coreAtom = new ResourceCoreAtomDmo();
		coreAtom.setResourceId(nextLong());
		coreAtom.setResourceType("olson");
		coreAtom.setAtomId(nextLong());
		coreAtom.setAtomLocus("madison avenue");
		coreAtom.setAtomType("draper");
		return coreAtom;
	}
}
