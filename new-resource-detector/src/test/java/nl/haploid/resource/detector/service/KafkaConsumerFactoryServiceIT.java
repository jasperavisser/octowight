package nl.haploid.resource.detector.service;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class KafkaConsumerFactoryServiceIT extends AbstractIT {

	@Autowired
	private KafkaConsumerFactoryService service;

	@Test
	public void testCreateKafkaConsumer() {
		final ConsumerConnector kafkaConsumer = service.createKafkaConsumer();
		assertNotNull(kafkaConsumer);
	}

	@Test
	public void testCreateStream() {
		final String topic = TestData.topic();
		final ConsumerConnector kafkaConsumer = service.createKafkaConsumer();
		final KafkaStream<byte[], byte[]> stream = service.createStream(kafkaConsumer, topic);
		assertNotNull(stream);
	}
}
