package nl.haploid.octowight.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import mockit.*;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.kafka.KafkaConsumerFactory;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EventConsumerServiceTest {

	@Tested
	private EventConsumerService consumerService;

	@Injectable
	private KafkaConsumerFactory consumerFactoryService;

	@Test
	public void testConsumeMessage(final @Mocked KafkaStream<byte[], byte[]> stream,
								   final @Mocked ConsumerIterator<byte[], byte[]> iterator,
								   final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
								   final @Injectable("my-topic") String topic)
			throws InterruptedException, ExecutionException {
		final String expectedMessage = TestData.message();
		new Expectations(consumerService) {{
			consumerService.getStream();
			times = 1;
			result = stream;
			stream.iterator();
			times = 1;
			result = iterator;
			iterator.next();
			times = 1;
			result = messageAndMetaData;
			messageAndMetaData.message();
			times = 1;
			result = expectedMessage.getBytes();
		}};
		final String actualMessage = consumerService.consumeMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testConsumeMessages(final @Mocked KafkaStream<byte[], byte[]> stream,
									final @Mocked ConsumerIterator<byte[], byte[]> iterator,
									final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
									final @Injectable("my-topic") String topic)
			throws InterruptedException, ExecutionException {
		final String message1 = TestData.message();
		final String message2 = TestData.message();
		final List<String> expectedMessages = Arrays.asList(message1, message2);
		new StrictExpectations(consumerService) {{
			consumerService.getStream();
			times = 1;
			result = stream;
			stream.iterator();
			times = 1;
			result = iterator;
			iterator.next();
			times = 1;
			result = messageAndMetaData;
			messageAndMetaData.message();
			times = 1;
			result = message1.getBytes();
			iterator.next();
			times = 1;
			result = messageAndMetaData;
			messageAndMetaData.message();
			times = 1;
			result = message2.getBytes();
			iterator.next();
			times = 1;
			result = new ConsumerTimeoutException();
		}};
		final List<String> actualMessages = consumerService.consumeMessages(expectedMessages.size() + 1)
				.collect(Collectors.toList());
		assertEquals(expectedMessages, actualMessages);
	}

	@Test
	public void testCommit(final @Mocked ConsumerConnector kafkaConsumer) {
		new StrictExpectations(consumerService) {{
			consumerService.getKafkaConsumer();
			times = 1;
			result = kafkaConsumer;
			kafkaConsumer.commitOffsets();
			times = 1;
		}};
		consumerService.commit();
	}
}
