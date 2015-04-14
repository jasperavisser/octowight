package nl.haploid.octowight.service;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import mockit.*;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
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

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	public void testConsumeMessage(final @Mocked KafkaStream<byte[], byte[]> stream,
								   final @Mocked ConsumerIterator<byte[], byte[]> iterator,
								   final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
								   final @Injectable("my-topic") String topic)
			throws InterruptedException, ExecutionException {
		final AtomChangeEvent expectedEvent = TestData.atomChangeEvent("rick");
		final String message = TestData.message();
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
			result = message.getBytes();
			jsonMapper.parse(message, AtomChangeEvent.class);
			times = 1;
			result = expectedEvent;
		}};
		final AtomChangeEvent actualEvent = consumerService.consumeMessage();
		assertEquals(expectedEvent, actualEvent);
	}

	@Test
	public void testConsumeMessages(final @Mocked KafkaStream<byte[], byte[]> stream,
									final @Mocked ConsumerIterator<byte[], byte[]> iterator,
									final @Mocked MessageAndMetadata<byte[], byte[]> messageAndMetaData,
									final @Injectable("my-topic") String topic)
			throws InterruptedException, ExecutionException {
		final AtomChangeEvent event1 = TestData.atomChangeEvent("carol");
		final AtomChangeEvent event2 = TestData.atomChangeEvent("daryl");
		final String message1 = TestData.message();
		final String message2 = TestData.message();
		final List<AtomChangeEvent> expectedEvents = Arrays.asList(event1, event2);
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
			jsonMapper.parse(message1, AtomChangeEvent.class);
			times = 1;
			result = event1;
			iterator.next();
			times = 1;
			result = messageAndMetaData;
			messageAndMetaData.message();
			times = 1;
			result = message2.getBytes();
			jsonMapper.parse(message2, AtomChangeEvent.class);
			times = 1;
			result = event2;
			iterator.next();
			times = 1;
			result = new ConsumerTimeoutException();
		}};
		final List<AtomChangeEvent> actualEvents = consumerService.consumeMessages(expectedEvents.size() + 1)
				.collect(Collectors.toList());
		assertEquals(expectedEvents, actualEvents);
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
