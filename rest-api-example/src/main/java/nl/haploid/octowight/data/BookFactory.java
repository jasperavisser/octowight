package nl.haploid.octowight.data;

import nl.haploid.octowight.repository.BookDmo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// TODO: test
@Component
public class BookFactory {

	public Book fromBookDmo(final BookDmo bookDmo) {
		final Book book = new Book();
		book.setGenre(bookDmo.getGenre());
		book.setId(bookDmo.getId());
		book.setTitle(bookDmo.getTitle());
		return book;
	}

	public List<Book> fromBookDmos(final Collection<BookDmo> bookDmos) {
		return bookDmos.stream()
				.map(this::fromBookDmo)
				.collect(Collectors.toList());
	}
}
