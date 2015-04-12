package nl.haploid.event.channel.service;

import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.AbstractIT;
import nl.haploid.event.channel.TestData;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import nl.haploid.event.channel.repository.RowChangeEventDmoRepository;
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
	private RowChangeEventDmoRepository repository;

	@Test
	@Transactional
	public void testQueueRowChangeEvents() throws Exception {
		final RowChangeEventDmo event = TestData.rowChangeEventDmo();
		repository.saveAndFlush(event);
		final int expectedCount = 1;
		final int actualCount = service.queueRowChangeEvents();
		assertEquals(expectedCount, actualCount);
	}

	@Test
	public void testProduceEvent() throws Exception {
		final RowChangeEvent event = TestData.rowChangeEvent();
		final Future<RecordMetadata> future = service.produceEvent(event);
		assertNotNull(future.get());
	}

	@Test
	public void testProduceEvents() throws Exception {
		final RowChangeEvent event = TestData.rowChangeEvent();
		final List<RecordMetadata> results = service.produceEvents(Arrays.asList(event));
		final int expectedCount = 1;
		final int actualCount = results.size();
		assertEquals(expectedCount, actualCount);
	}
}
