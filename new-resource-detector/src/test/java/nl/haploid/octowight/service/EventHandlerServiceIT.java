package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.detector.MockResourceDetector;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class EventHandlerServiceIT extends AbstractIT {

	@Autowired
	private EventHandlerService service;

	@Autowired
	private EventConsumerService consumerService;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private JsonMapper jsonMapper;

	@Test
	public void testHandleEventsNone() throws Exception {
		final long actualCount = service.handleEvents(10);
		assertEquals(0, actualCount);
	}

	@Test
	public void testHandleEvents() throws Exception {
		final String topic = TestData.topic();
		consumerService.setTopic(topic);
		final AtomChangeEvent event1 = TestData.atomChangeEvent(MockResourceDetector.ATOM_TYPE);
		final AtomChangeEvent event2 = TestData.atomChangeEvent(MockResourceDetector.ATOM_TYPE);
		final AtomChangeEvent event3 = TestData.atomChangeEvent("jack");
		sendMessage(topic, event1);
		sendMessage(topic, event2);
		sendMessage(topic, event3);
		final long actualCount = service.handleEvents(10);
		assertEquals(2, actualCount);
	}

	public void sendMessage(final String topic, final AtomChangeEvent event) throws InterruptedException, ExecutionException {
		final String message = jsonMapper.serialize(event);
		log.debug(String.format("Send message: %s", message));
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
		kafkaProducer.send(record).get();
	}
}
