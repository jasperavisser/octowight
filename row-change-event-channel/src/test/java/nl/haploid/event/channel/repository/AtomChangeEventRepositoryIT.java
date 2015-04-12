package nl.haploid.event.channel.repository;

import nl.haploid.event.channel.AbstractIT;
import nl.haploid.event.channel.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AtomChangeEventRepositoryIT extends AbstractIT {

	@Autowired
	private AtomChangeEventDmoRepository repository;

	@Test
	@Transactional
	public void testFindAll() {
		final AtomChangeEventDmo event = TestData.atomChangeEventDmo();
		repository.saveAndFlush(event);
		final List<AtomChangeEventDmo> events = repository.findAll();
		assertEquals(1, events.size());
	}
}
