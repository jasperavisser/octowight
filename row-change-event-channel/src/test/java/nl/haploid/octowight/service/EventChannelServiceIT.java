package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.repository.AtomChangeEventDmo;
import nl.haploid.octowight.repository.AtomChangeEventDmoRepository;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventChannelServiceIT extends AbstractIT {

	@Autowired
	private EventChannelService service;

	@Autowired
	private AtomChangeEventDmoRepository repository;

	@Test
	@Transactional
	public void testQueueAtomChangeEvents() throws Exception {
		final AtomChangeEventDmo event = TestData.atomChangeEventDmo();
		repository.saveAndFlush(event);
		final int expectedCount = 1;
		final int actualCount = service.queueAtomChangeEvents();
		assertEquals(expectedCount, actualCount);
	}

	@Test
	public void testProduceEvent() throws Exception {
		final AtomChangeEvent event = TestData.atomChangeEvent();
		final Future<RecordMetadata> future = service.produceEvent(event);
		assertNotNull(future.get());
	}

	@Test
	public void testProduceEvents() throws Exception {
		final AtomChangeEvent event = TestData.atomChangeEvent();
		final List<RecordMetadata> results = service.produceEvents(Collections.singletonList(event));
		final int expectedCount = 1;
		final int actualCount = results.size();
		assertEquals(expectedCount, actualCount);
	}
}
