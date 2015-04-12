package nl.haploid.event.channel.service;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.AbstractIT;
import nl.haploid.event.channel.TestData;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;
import nl.haploid.event.channel.repository.AtomChangeEventDmoRepository;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
		final List<RecordMetadata> results = service.produceEvents(Arrays.asList(event));
		final int expectedCount = 1;
		final int actualCount = results.size();
		assertEquals(expectedCount, actualCount);
	}
}
