package nl.haploid.octowight.registry;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.repository.ResourceElementDmo;
import nl.haploid.octowight.registry.repository.ResourceModelDmo;
import nl.haploid.octowight.registry.repository.ResourceModelId;
import nl.haploid.octowight.registry.repository.ResourceRootDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static String nextString() {
		return UUID.randomUUID().toString();
	}

	public static ResourceRoot resourceRoot() {
		return resourceRoot(nextLong());
	}

	public static ResourceRoot resourceRoot(final Long resourceId) {
		final ResourceRoot resourceRoot = new ResourceRoot();
		resourceRoot.setResourceId(resourceId);
		resourceRoot.setResourceType("olson");
		resourceRoot.setAtomId(nextLong());
		resourceRoot.setAtomOrigin("madison avenue");
		resourceRoot.setAtomType("draper");
		return resourceRoot;
	}

	public static AtomChangeEvent atomChangeEvent(final AtomGroup atomGroup) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomOrigin(atomGroup.getAtomOrigin());
		event.setAtomType(atomGroup.getAtomType());
		return event;
	}

	public static AtomChangeEvent atomChangeEvent(final String atomType) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(nextLong());
		event.setAtomId(nextLong());
		event.setAtomOrigin("everywhere");
		event.setAtomType(atomType);
		return event;
	}

	public static ResourceRootDmo resourceRootDmo() {
		return resourceRootDmo("olson");
	}

	public static ResourceRootDmo resourceRootDmo(final String resourceType) {
		final ResourceRootDmo dmo = new ResourceRootDmo();
		dmo.setResourceId(nextLong());
		dmo.setResourceType(resourceType);
		dmo.setAtomId(nextLong());
		dmo.setAtomOrigin("madison avenue");
		dmo.setAtomType("draper");
		dmo.setVersion(nextLong());
		return dmo;
	}

	public static ResourceElementDmo resourceElementDmo(final ResourceRootDmo resourceRootDmo, final AtomChangeEvent atomChangeEvent) {
		final ResourceElementDmo dmo = new ResourceElementDmo();
		dmo.setResourceId(resourceRootDmo.getResourceId());
		dmo.setResourceType(resourceRootDmo.getResourceType());
		dmo.setAtomId(atomChangeEvent.getAtomId());
		dmo.setAtomOrigin(atomChangeEvent.getAtomOrigin());
		dmo.setAtomType(atomChangeEvent.getAtomType());
		return dmo;
	}

	public static ResourceElementDmo resourceElementDmo() {
		final ResourceElementDmo dmo = new ResourceElementDmo();
		dmo.setResourceId(nextLong());
		dmo.setResourceType("creative director");
		dmo.setAtomId(nextLong());
		dmo.setAtomOrigin("madison avenue");
		dmo.setAtomType("draper");
		return dmo;
	}

	public static ResourceModelDmo resourceModelDmo() {
		final ResourceModelDmo expectedResourceModelDmo = new ResourceModelDmo();
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(TestData.nextLong());
		resourceModelId.setResourceType(TestData.nextString());
		expectedResourceModelDmo.setId(resourceModelId);
		expectedResourceModelDmo.setBody(TestData.nextString());
		return expectedResourceModelDmo;
	}

	public static AtomGroup atomGroup() {
		final AtomGroup atomGroup = new AtomGroup();
		atomGroup.setAtomOrigin("new york");
		atomGroup.setAtomType("advertising agency");
		return atomGroup;
	}

	public static ResourceModelId resourceModelId() {
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(nextLong());
		resourceModelId.setResourceType(nextString());
		return resourceModelId;
	}
}
