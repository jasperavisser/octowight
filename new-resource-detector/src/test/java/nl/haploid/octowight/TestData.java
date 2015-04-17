package nl.haploid.octowight;

import nl.haploid.octowight.registry.data.Resource;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static Resource resource(final Long resourceId) {
		final Resource resource = new Resource();
		resource.setResourceId(resourceId);
		resource.setResourceType("olson");
		resource.setAtomId(nextLong());
		resource.setAtomLocus("madison avenue");
		resource.setAtomType("draper");
		return resource;
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
