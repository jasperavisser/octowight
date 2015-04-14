package nl.haploid.octowight;

import nl.haploid.octowight.service.ResourceDescriptor;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static ResourceDescriptor resourceDescriptor(final Long resourceId) {
		final ResourceDescriptor descriptor = new ResourceDescriptor();
		descriptor.setResourceId(resourceId);
		descriptor.setResourceType("olson");
		descriptor.setAtomId(nextLong());
		descriptor.setAtomLocus("madison avenue");
		descriptor.setAtomType("draper");
		return descriptor;
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
