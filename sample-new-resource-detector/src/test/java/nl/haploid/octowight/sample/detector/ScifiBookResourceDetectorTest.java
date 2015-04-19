package nl.haploid.octowight.sample.detector;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceFactory;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.BookDmo;
import nl.haploid.octowight.sample.repository.BookDmoRepository;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ScifiBookResourceDetectorTest {

	@Tested
	private ScifiBookResourceDetector detector;

	@Injectable
	private BookDmoRepository bookDmoRepository;

	@Injectable
	private ResourceFactory resourceFactory;

	@Test
	public void testGetAtomTypes() throws Exception {
		assertEquals(1, detector.getAtomTypes().size());
	}

	@Test
	public void testDetect() throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final AtomChangeEvent event3 = TestData.atomChangeEvent();
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2, event3);
		final Long id1 = event1.getAtomId();
		final Long id2 = event2.getAtomId();
		final BookDmo book1 = TestData.bookDmo("war", id1);
		final BookDmo book2 = TestData.bookDmo(ScifiBookResourceDetector.GENRE, id2);
		final HashMap<Long, BookDmo> booksById = new HashMap<Long, BookDmo>() {{
			put(id1, book1);
			put(id2, book2);
		}};
		final Resource resource = TestData.resource(id2);
		final List<Resource> expectedResources = Collections.singletonList(resource);
		new StrictExpectations(detector) {{
			detector.getBooksById(events);
			times = 1;
			result = booksById;
			resourceFactory.fromAtomChangeEvent(event2, ScifiBookResourceDetector.RESOURCE_TYPE);
			times = 1;
			result = resource;
		}};
		final List<Resource> actualResources = detector.detect(events);
		assertEquals(expectedResources, actualResources);
	}

	@Test
	public void testGetBooksById() throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final Long id1 = event1.getAtomId();
		final Long id2 = event2.getAtomId();
		final BookDmo book1 = TestData.bookDmo("romance", id1);
		final BookDmo book2 = TestData.bookDmo("non-fiction", id2);
		new StrictExpectations() {{
			bookDmoRepository.findAll(Arrays.asList(id1, id2));
			times = 1;
			result = Arrays.asList(book1, book2);
		}};
		final Map<Long, BookDmo> booksById = detector.getBooksById(events);
		assertEquals(book1, booksById.get(id1));
		assertEquals(book2, booksById.get(id2));
	}
}