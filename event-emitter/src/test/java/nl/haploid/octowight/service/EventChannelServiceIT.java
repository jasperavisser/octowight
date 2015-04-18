package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.TestData;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EventChannelServiceIT extends AbstractIT {

	@Autowired
	private EventChannelService service;

	@Test
	public void testSendEvent() throws Exception {
		final AtomChangeEvent event = TestData.atomChangeEvent();
		final Future<RecordMetadata> future = service.sendEvent(event);
		assertNotNull(future.get());
	}

	@Test
	public void testSendEvents() throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final List<RecordMetadata> results = service.sendEvents(Arrays.asList(event1, event2));
		final int expectedCount = 2;
		final int actualCount = results.size();
		assertEquals(expectedCount, actualCount);
	}
}
