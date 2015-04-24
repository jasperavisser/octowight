package nl.haploid.octowight.sample;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static AtomChangeEvent atomChangeEvent() {
		return atomChangeEvent(nextLong());
	}

	public static AtomChangeEvent atomChangeEvent(final long atomId) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setAtomId(atomId);
		event.setAtomLocus(name());
		event.setAtomType(PersonDmo.ATOM_TYPE);
		return event;
	}

	public static String name() {
		return UUID.randomUUID().toString();
	}

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static PersonDmo personDmo() {
		return personDmo(nextLong());
	}

	public static PersonDmo personDmo(final long id) {
		final PersonDmo dmo = new PersonDmo();
		dmo.setId(id);
		dmo.setName(name());
		return dmo;
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

	public static RoleDmo roleDmo(final PersonDmo personDmo, final String type) {
		final RoleDmo dmo = new RoleDmo();
		dmo.setId(nextLong());
		dmo.setPerson(personDmo);
		dmo.setType(type);
		return dmo;
	}

	public static String topic() {
		return UUID.randomUUID().toString();
	}
}
