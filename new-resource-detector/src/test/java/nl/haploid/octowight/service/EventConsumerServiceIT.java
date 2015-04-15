package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EventConsumerServiceIT extends AbstractIT {

	@Autowired
	private EventConsumerService service;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private JsonMapper jsonMapper;

	@Rule
	public Timeout globalTimeout = new Timeout(10, TimeUnit.SECONDS);

	@Test
	public void testConsumeMessage() throws InterruptedException, ExecutionException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final AtomChangeEvent expectedEvent = TestData.atomChangeEvent("joan");
		sendMessage(topic, expectedEvent);
		final AtomChangeEvent actualEvent = service.consumeMessage();
		assertEquals(expectedEvent, actualEvent);
	}

	public void sendMessage(final String topic, final AtomChangeEvent event) throws InterruptedException, ExecutionException {
		final String message = jsonMapper.toString(event);
		log.debug(String.format("Send message: %s", message));
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
		kafkaProducer.send(record).get();
	}

	@Test
	public void testConsumeMessages() throws InterruptedException, ExecutionException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final AtomChangeEvent event1 = TestData.atomChangeEvent("bob");
		final AtomChangeEvent event2 = TestData.atomChangeEvent("benson");
		final List<AtomChangeEvent> expectedEvents = Arrays.asList(event1, event2);
		sendMessage(topic, event1);
		sendMessage(topic, event2);
		final List<AtomChangeEvent> actualEvents = service.consumeMessages(10)
				.collect(Collectors.toList());
		assertEquals(expectedEvents, actualEvents);
	}

	@Test
	public void testCommit() throws ExecutionException, InterruptedException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final AtomChangeEvent event = TestData.atomChangeEvent("harris");
		sendMessage(topic, event);
		service.consumeMessages(10)
				.collect(Collectors.toList());
		service.commit();
		service.reset();
		final List<AtomChangeEvent> actualEvents = service.consumeMessages(10)
				.collect(Collectors.toList());
		assertEquals(0, actualEvents.size());
	}
}
