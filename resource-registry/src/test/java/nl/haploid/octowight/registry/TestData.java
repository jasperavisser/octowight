package nl.haploid.octowight.registry;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import nl.haploid.octowight.registry.repository.ResourceElementDmo;

import java.util.Random;

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

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomLocus("everywhere");
		event.setAtomType(atomType);
		return event;
	}

	public static ResourceRootDmo resourceRootDmo() {
		return resourceRootDmo("olson");
	}

	public static ResourceRootDmo resourceRootDmo(final String resourceType) {
		final ResourceRootDmo dmo = new ResourceRootDmo();
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
