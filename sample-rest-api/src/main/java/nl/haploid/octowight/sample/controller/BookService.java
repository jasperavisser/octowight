package nl.haploid.octowight.sample.controller;

import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import nl.haploid.octowight.sample.data.Book;
import nl.haploid.octowight.sample.data.BookFactory;
import nl.haploid.octowight.sample.repository.BookDmo;
import nl.haploid.octowight.sample.repository.BookDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// TODO: tests
@Service
public class BookService {

	// TODO: doesn't match resource type; maybe product, subcategory=book
	public static final String RESOURCE_TYPE = "scifi-book";

	@Autowired
	private BookDmoRepository bookRepository;

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private BookFactory bookFactory;

	public Book getBook(final long resourceId) {
		final ResourceDmo resourceDmo = resourceRepository.findByResourceTypeAndResourceId(RESOURCE_TYPE, resourceId);
		// TODO: handle non-existent (404)
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
