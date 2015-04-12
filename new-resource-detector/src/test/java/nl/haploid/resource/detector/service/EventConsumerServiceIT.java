package nl.haploid.resource.detector.service;

import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

public class EventConsumerServiceIT extends AbstractIT {

	@Autowired
	private EventConsumerService service;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Rule
	public Timeout globalTimeout = new Timeout(10000);

	@Test
	public void testConsumeMessage() throws InterruptedException, ExecutionException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final String expectedMessage = UUID.randomUUID().toString();
		sendMessage(topic, expectedMessage);
		final String actualMessage = service.consumeMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	public void sendMessage(final String topic, final String expectedMessage) throws InterruptedException, ExecutionException {
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, expectedMessage);
		kafkaProducer.send(record).get();
	}

	@Test
	public void testConsumeMessages() throws InterruptedException, ExecutionException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final String message1 = UUID.randomUUID().toString();
		final String message2 = UUID.randomUUID().toString();
		final List<String> expectedMessages = Arrays.asList(message1, message2);
		sendMessage(topic, message1);
		sendMessage(topic, message2);
		final List<String> actualMessages = service.consumeMessages(10);
		assertEquals(expectedMessages, actualMessages);
	}

	@Test
	public void testCommit() throws ExecutionException, InterruptedException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final String message = UUID.randomUUID().toString();
		sendMessage(topic, message);
		service.consumeMessages(10);
		service.commit();
		service.reset();
		final List<String> actualMessages = service.consumeMessages(10);
		assertEquals(0, actualMessages.size());
	}
}
