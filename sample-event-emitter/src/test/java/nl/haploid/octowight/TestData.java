package nl.haploid.octowight;

import nl.haploid.octowight.repository.AtomChangeEventDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static final String ATOM_LOCUS = "sunnydale";

	public static final String ATOM_TYPE = "spike";

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static AtomChangeEventDmo atomChangeEventDmo() {
		final AtomChangeEventDmo event = new AtomChangeEventDmo();
		event.setAtomId(nextLong());
		event.setAtomLocus(ATOM_LOCUS);
		event.setAtomType(ATOM_TYPE);
		return event;
	}

	public static String topic() {
		return UUID.randomUUID().toString();
	}
}
