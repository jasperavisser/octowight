package nl.haploid.octowight;

import java.util.UUID;

public class TestData {

	public static String topic() {
		return nextString();
	}

	public static String nextString() {
		return UUID.randomUUID().toString();
	}
}
