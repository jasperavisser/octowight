package nl.haploid.octowight.sample;

import nl.haploid.octowight.sample.repository.BookDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static BookDmo bookDmo(final String genre) {
		final BookDmo book = new BookDmo();
		book.setId(null);
		book.setGenre(genre);
		book.setTitle(title());
		return book;
	}

	public static long nextLong() {
		return new Random().nextLong();
	}

	public static String title() {
		return UUID.randomUUID().toString();
	}
}
