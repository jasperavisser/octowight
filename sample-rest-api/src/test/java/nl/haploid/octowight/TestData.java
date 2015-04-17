package nl.haploid.octowight;

import nl.haploid.octowight.repository.BookDmo;

import java.util.UUID;

public class TestData {

	public static BookDmo bookDmo(final String genre) {
		final BookDmo book = new BookDmo();
		book.setId(null);
		book.setGenre(genre);
		book.setTitle(title());
		return book;
	}

	public static String title() {
		return UUID.randomUUID().toString();
	}
}
