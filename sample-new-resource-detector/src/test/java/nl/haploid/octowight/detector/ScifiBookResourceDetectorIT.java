package nl.haploid.octowight.detector;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ScifiBookResourceDetectorIT extends AbstractIT {

	@Autowired
	private ScifiBookResourceDetector detector;

	@Autowired
	private BookDmoRepository bookDmoRepository;

	@Test
	public void testGetBooksById() {
		final BookDmo expectedDmo = bookDmoRepository.save(TestData.bookDmo(ScifiBookResourceDetector.GENRE));
		bookDmoRepository.save(TestData.bookDmo("religion"));
		final AtomChangeEvent event1 = TestData.atomChangeEvent(expectedDmo.getId());
		final List<AtomChangeEvent> events = Collections.singletonList(event1);
		final Map<Long, BookDmo> dmosById = detector.getBooksById(events);
		assertEquals(1, dmosById.size());
		assertEquals(expectedDmo, dmosById.get(expectedDmo.getId()));
	}

	@Test
	public void testDetect() throws Exception {
		final BookDmo dmo1 = bookDmoRepository.save(TestData.bookDmo(ScifiBookResourceDetector.GENRE));
		final BookDmo dmo2 = bookDmoRepository.save(TestData.bookDmo("religion"));
		final AtomChangeEvent event1 = TestData.atomChangeEvent(dmo1.getId());
		final AtomChangeEvent event2 = TestData.atomChangeEvent(dmo2.getId());
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<Resource> actualResources = detector.detect(events);
		assertEquals(1, actualResources.size());
		assertEquals(dmo1.getId(), actualResources.get(0).getAtomId());
	}
}
