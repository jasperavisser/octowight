package nl.haploid.octowight;

import nl.haploid.octowight.registry.data.ResourceRoot;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static ResourceRoot resourceRoot(final Long resourceId) {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceId(resourceId);
		resourceRoot.setResourceType("olson");
		resourceRoot.setAtomId(nextLong());
		resourceRoot.setAtomLocus("madison avenue");
		resourceRoot.setAtomType("draper");
		return resourceRoot;
	}

	public static String message() {
		return nextString();
	}

	public static String topic() {
		return nextString();
	}

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomLocus("everywhere");
		event.setAtomType(atomType);
		return event;
	}

	public static String nextString() {
		return UUID.randomUUID().toString();
	}
}
