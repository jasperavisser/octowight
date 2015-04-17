package nl.haploid.octowight.controller;

import nl.haploid.octowight.data.Book;
import nl.haploid.octowight.data.BookFactory;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
import nl.haploid.octowight.repository.ResourceDmo;
import nl.haploid.octowight.repository.ResourceDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookService {

	// TODO: doesn't match resource type; maybe product, subcategory=book
	private static final String RESOURCE_TYPE = "scifi-book";

	@Autowired
	private BookDmoRepository bookRepository;

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private BookFactory bookFactory;

	public Book getBook(final long resourceId) {
		final ResourceDmo resourceDmo = resourceRepository.findByResourceTypeAndResourceId(RESOURCE_TYPE, resourceId);
		// TODO: handle non-existent (404)
		// TODO: tests
		final BookDmo bookDmo = bookRepository.findOne(resourceDmo.getAtomId());
		final Book book = bookFactory.fromBookDmo(bookDmo);
		book.setId(resourceDmo.getResourceId());
		return book;
	}

	public List<Book> getBooks() {
		final Map<Long, ResourceDmo> resourceDmos = resourceRepository
				.findByResourceType(RESOURCE_TYPE).stream()
				.collect(Collectors.toMap(ResourceDmo::getAtomId, Function.identity()));
		final List<BookDmo> bookDmos = bookRepository.findAll(resourceDmos.keySet());
		return bookFactory.fromBookDmos(bookDmos).stream()
				.map(book -> {
					book.setId(resourceDmos.get(book.getId()).getResourceId());
					return book;
				})
				.collect(Collectors.toList());
	}
}
