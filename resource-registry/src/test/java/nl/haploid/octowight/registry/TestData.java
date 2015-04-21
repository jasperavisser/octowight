package nl.haploid.octowight.registry;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceElementDmo;

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
		return resourceDmo("olson");
	}

	public static ResourceDmo resourceDmo(final String resourceType) {
		final ResourceDmo dmo = new ResourceDmo();
		dmo.setResourceType(resourceType);
		dmo.setAtomId(nextLong());
		dmo.setAtomLocus("madison avenue");
		dmo.setAtomType("draper");
		return dmo;
	}

	public static ResourceElementDmo resourceElementDmo() {
		final ResourceElementDmo dmo = new ResourceElementDmo();
		dmo.setResourceId(nextLong());
		dmo.setResourceType("creative director");
		dmo.setAtomId(nextLong());
		dmo.setAtomLocus("madison avenue");
		dmo.setAtomType("draper");
		return dmo;
	}
}
