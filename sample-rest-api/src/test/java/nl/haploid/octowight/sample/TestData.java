package nl.haploid.octowight.sample;

import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.sample.data.Captain;
import nl.haploid.octowight.sample.repository.PersonDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static PersonDmo personDmo() {
		return personDmo(null);
	}

	public static PersonDmo personDmo(final Long id) {
		final PersonDmo person = new PersonDmo();
		person.setId(id);
		person.setName(nextString());
		return person;
	}

	public static String nextString() {
		return UUID.randomUUID().toString();
	}

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static ResourceDmo resourceDmo(final long resourceId) {
		final ResourceDmo dmo = new ResourceDmo();
		dmo.setAtomId(nextLong());
		dmo.setAtomLocus(nextString());
		dmo.setAtomType(nextString());
		dmo.setResourceId(resourceId);
		dmo.setResourceType(nextString());
		return dmo;
	}

	public static Captain captain() {
		final Captain captain = new Captain();
		captain.setName(nextString());
		return captain;
	}
}
