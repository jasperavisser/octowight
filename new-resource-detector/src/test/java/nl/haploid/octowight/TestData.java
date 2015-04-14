package nl.haploid.octowight;

import nl.haploid.octowight.data.ResourceCoreAtom;

import java.util.Random;
import java.util.UUID;

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

	public static String message() {
		return UUID.randomUUID().toString();
	}

	public static String topic() {
		return UUID.randomUUID().toString();
	}

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomLocus("everywhere");
		event.setAtomType(atomType);
		return event;
	}
}
