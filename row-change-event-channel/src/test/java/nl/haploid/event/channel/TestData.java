package nl.haploid.event.channel;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static final String ATOM_LOCUS = "sunnydale";

	public static final String ATOM_TYPE = "spike";

	public static AtomChangeEvent atomChangeEvent() {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setAtomId(nextLong());
		event.setAtomLocus(ATOM_LOCUS);
		event.setAtomType(ATOM_TYPE);
		return event;
	}

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
