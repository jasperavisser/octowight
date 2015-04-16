package nl.haploid.octowight;

import nl.haploid.octowight.data.Resource;
import nl.haploid.octowight.repository.BookDmo;

import java.util.Random;
import java.util.UUID;

public class TestData {

	public static final String ATOM_LOCUS = "library";

	public static final String ATOM_TYPE = "book";

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

	public static String topic() {
		return UUID.randomUUID().toString();
	}

	public static AtomChangeEvent atomChangeEvent() {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setAtomId(nextLong());
		event.setAtomLocus(ATOM_LOCUS);
		event.setAtomType(ATOM_TYPE);
		return event;
	}

	public static Resource resource(final Long resourceId) {
		final Resource resource = new Resource();
		resource.setResourceId(resourceId);
		resource.setResourceType("olson");
		resource.setAtomId(nextLong());
		resource.setAtomLocus("madison avenue");
		resource.setAtomType("draper");
		return resource;
	}
}
