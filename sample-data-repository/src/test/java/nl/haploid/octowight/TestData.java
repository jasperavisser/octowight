package nl.haploid.octowight;

import nl.haploid.octowight.repository.BookDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static BookDmo bookDmo(final String genre) {
		return bookDmo(genre, nextLong());
	}

	public static BookDmo bookDmo(final String genre, final long id) {
		final BookDmo book = new BookDmo();
		book.setId(id);
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
