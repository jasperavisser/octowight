package nl.haploid.octowight.kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.TestData;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class KafkaConsumerFactoryTest {

	@Tested
	KafkaConsumerFactory service;

	@Injectable
	private ConsumerConfig consumerConfig;

	@Test
	@SuppressWarnings("unchecked")
	public void testCreateStream(final @Mocked ConsumerConnector kafkaConsumer,
	                             final @Mocked KafkaStream<byte[], byte[]> expectedStream) {
		final String topic = TestData.topic();
		final Map<String, List<KafkaStream<byte[], byte[]>>> streams = new HashMap<>();
		streams.put(topic, Collections.singletonList(expectedStream));
		new StrictExpectations() {{
			kafkaConsumer.createMessageStreams((Map<String, Integer>) any);
			times = 1;
			result = streams;
		}};
		final KafkaStream<byte[], byte[]> actualStream = service.createStream(kafkaConsumer, topic);
		assertEquals(expectedStream, actualStream);
	}
}
