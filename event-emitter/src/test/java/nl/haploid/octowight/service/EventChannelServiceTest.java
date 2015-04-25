package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class EventChannelServiceTest {

	@Tested
	private EventChannelService service;

	@Injectable
	private KafkaProducer<String, String> kafkaProducer;

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	public void testSendEvents(final @Mocked Future<RecordMetadata> future1,
							   final @Mocked Future<RecordMetadata> future2) throws Exception {
		final AtomChangeEvent event1 = TestData.atomChangeEvent();
		final AtomChangeEvent event2 = TestData.atomChangeEvent();
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final String message1 = TestData.message();
		final String message2 = TestData.message();
		new StrictExpectations() {{
			jsonMapper.serialize(event1);
			times = 1;
			result = message1;
			kafkaProducer.send((ProducerRecord<String, String>) any);
			times = 1;
			result = future1;
			jsonMapper.serialize(event2);
			times = 1;
			result = message2;
			kafkaProducer.send((ProducerRecord<String, String>) any);
			times = 1;
			result = future2;
			future1.get(anyLong, TimeUnit.SECONDS);
			times = 1;
			future2.get(anyLong, TimeUnit.SECONDS);
			times = 1;
		}};
		service.sendEvents(events);
	}
}
