package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
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

	@Rule
	public Timeout globalTimeout = new Timeout(10, TimeUnit.SECONDS);

	@Test
	public void testConsumeMessage() throws InterruptedException, ExecutionException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final String expectedMessage = TestData.message();
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
		final String message1 = TestData.message();
		final String message2 = TestData.message();
		final List<String> expectedMessages = Arrays.asList(message1, message2);
		sendMessage(topic, message1);
		sendMessage(topic, message2);
		final List<String> actualMessages = service.consumeMessages(10)
				.collect(Collectors.toList());
		assertEquals(expectedMessages, actualMessages);
	}

	@Test
	public void testCommit() throws ExecutionException, InterruptedException {
		final String topic = TestData.topic();
		service.setTopic(topic);
		final String message = TestData.message();
		sendMessage(topic, message);
		service.consumeMessages(10)
				.collect(Collectors.toList());
		service.commit();
		service.reset();
		final List<String> actualMessages = service.consumeMessages(10)
				.collect(Collectors.toList());
		assertEquals(0, actualMessages.size());
	}
}
