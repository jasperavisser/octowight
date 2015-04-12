package nl.haploid.event.channel.repository;

import nl.haploid.event.channel.AbstractIT;
import nl.haploid.event.channel.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RowChangeEventRepositoryIT extends AbstractIT {

	@Autowired
	private RowChangeEventDmoRepository repository;

	@Test
	@Transactional
	public void testFindAll() {
		final RowChangeEventDmo event = TestData.rowChangeEventDmo();
		repository.saveAndFlush(event);
		final List<RowChangeEventDmo> events = repository.findAll();
		assertEquals(1, events.size());
	}
}
