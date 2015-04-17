package nl.haploid.octowight.controller;

import nl.haploid.octowight.data.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

// TODO: tests
@Controller
@RequestMapping("/book")
@ResponseBody
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Book> getBooks() {
		return bookService.getBooks();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getBook(final @PathVariable long id) {
		// TODO: handle non-existent (404)
		// TODO: each resource may already have been demoted (not a resource anymore, but that info is still in event stream)
		return bookService.getBook(id);
	}
}
