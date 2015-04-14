package nl.haploid.octowight.repository;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BookDmoRepositoryIT extends AbstractIT {

	@Autowired
	private BookDmoRepository repository;

	@Test
	@Transactional
	public void testFindAll() {
		final BookDmo book = TestData.bookDmo("comedy");
		repository.saveAndFlush(book);
		final List<BookDmo> books = repository.findAll();
		assertEquals(1, books.size());
	}
}
