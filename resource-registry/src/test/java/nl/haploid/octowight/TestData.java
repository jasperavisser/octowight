package nl.haploid.octowight;

import nl.haploid.octowight.data.Resource;
import nl.haploid.octowight.repository.ResourceDmo;

import java.util.Random;

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

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomLocus("everywhere");
		event.setAtomType(atomType);
		return event;
	}

	public static ResourceDmo resourceDmo() {
		final ResourceDmo resource = new ResourceDmo();
		resource.setResourceId(nextLong());
		resource.setResourceType("olson");
		resource.setAtomId(nextLong());
		resource.setAtomLocus("madison avenue");
		resource.setAtomType("draper");
		return resource;
	}
}
