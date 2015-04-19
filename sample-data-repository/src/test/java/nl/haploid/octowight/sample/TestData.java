package nl.haploid.octowight.sample;

import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static String name() {
		return UUID.randomUUID().toString();
	}

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static PersonDmo personDmo() {
		final PersonDmo dmo = new PersonDmo();
		dmo.setId(nextLong());
		dmo.setName(name());
		return dmo;
	}

	public static RoleDmo roleDmo(final PersonDmo personDmo, final String type) {
		final RoleDmo dmo = new RoleDmo();
		dmo.setId(nextLong());
		dmo.setPerson(personDmo);
		dmo.setType(type);
		return dmo;
	}
}
