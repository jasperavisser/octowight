package nl.haploid.event.channel;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;

import java.util.Random;

public class TestData {

	public static final String TABLE_NAME = "spike";

	public static AtomChangeEvent atomChangeEvent() {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setAtomType(TABLE_NAME);
		event.setAtomId((long) new Random().nextInt());
		return event;
	}

	public static AtomChangeEventDmo atomChangeEventDmo() {
		final AtomChangeEventDmo event = new AtomChangeEventDmo();
		event.setAtomType(TABLE_NAME);
		event.setAtomId((long) new Random().nextInt());
		return event;
	}
}
