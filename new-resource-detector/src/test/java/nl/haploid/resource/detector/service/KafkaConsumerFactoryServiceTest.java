package nl.haploid.resource.detector.service;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.resource.detector.TestData;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KafkaConsumerFactoryServiceTest {

	@Tested
	KafkaConsumerFactoryService service;

	@Injectable
	private ConsumerConfig consumerConfig;

	@Test
	@SuppressWarnings("unchecked")
	public void testCreateStream(final @Mocked ConsumerConnector kafkaConsumer,
								 final @Mocked KafkaStream<byte[], byte[]> expectedStream) {
		final String topic = TestData.topic();
		final Map<String, List<KafkaStream<byte[], byte[]>>> streams = new HashMap<>();
		streams.put(topic, Arrays.asList(expectedStream));
		new StrictExpectations() {{
			kafkaConsumer.createMessageStreams((Map<String, Integer>) any);
			times = 1;
			result = streams;
		}};
		final KafkaStream<byte[], byte[]> actualStream = service.createStream(kafkaConsumer, topic);
		assertEquals(expectedStream, actualStream);
	}
}
