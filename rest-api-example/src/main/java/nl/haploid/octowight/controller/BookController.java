package nl.haploid.octowight.controller;

import nl.haploid.octowight.data.Book;
import nl.haploid.octowight.data.BookFactory;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
import nl.haploid.octowight.repository.ResourceDmo;
import nl.haploid.octowight.repository.ResourceDmoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
@ResponseBody
public class BookController {

	private static final String RESOURCE_TYPE = "scifi-book"; // TODO: doesn't match resource type

	@Autowired
	private BookDmoRepository bookRepository;

	@Autowired
	private ResourceDmoRepository resourceRepository;

	@Autowired
	private BookFactory bookFactory;

	@RequestMapping(method = RequestMethod.GET)
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getBook(final @PathVariable long id) {
		final ResourceDmo resourceDmo = resourceRepository.findByResourceTypeAndResourceId(RESOURCE_TYPE, id);
		// TODO: handle non-existent (404)
		// TODO: tests
		// TODO: docker
		final BookDmo bookDmo = bookRepository.findOne(resourceDmo.getAtomId());
		final Book book = bookFactory.fromBookDmo(bookDmo);
		book.setId(resourceDmo.getResourceId());
		return book;
	}
}
