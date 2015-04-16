package nl.haploid.octowight.controller;

import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
import nl.haploid.octowight.repository.ResourceRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/book")
@ResponseBody
public class BookController {

	private static final String RESOURCE_TYPE = "scifi-book"; // TODO: doesn't match resource type

	@Autowired
	private BookDmoRepository repository;

	@Autowired
	private ResourceRegistryRepository resourceRepository;

	@RequestMapping(method = RequestMethod.GET)
	public List<BookDmo> getBooks() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public BookDmo getBook(final @PathVariable long id) {
		// TODO: get by resource id, not by atom id
		final ResourceCoreAtom coreAtom = resourceRepository.findByResourceTypeAndResourceId(RESOURCE_TYPE, id);
		// TODO: using redis
		// TODO: handle non-existent (404)
		// TODO: tests
		// TODO: docker
		return repository.findOne(id);
	}
}
