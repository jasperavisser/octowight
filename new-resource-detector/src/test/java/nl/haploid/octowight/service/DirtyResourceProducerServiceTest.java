package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.data.Resource;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

public class DirtyResourceProducerServiceTest {

	@Tested
	private DirtyResourceProducerService service;

	@Injectable
	private KafkaProducer<String, String> kafkaProducer;

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	@SuppressWarnings("unchecked")
	public void testSendDirtyResource(final @Mocked Future<RecordMetadata> expectedFuture) throws Exception {
		final Resource resource = TestData.resource(451l);
		final String message = "joy";
		new StrictExpectations() {{
			jsonMapper.toString(resource);
			times = 1;
			result = message;
			kafkaProducer.send((ProducerRecord<String, String>) any);
			times = 1;
			result = expectedFuture;
		}};
		final Future<RecordMetadata> actualFuture = service.sendDirtyResource(resource);
		assertEquals(expectedFuture, actualFuture);
	}

	@Test
	public void testResolveFuture(final @Mocked Future<RecordMetadata> future,
								  final @Mocked RecordMetadata expectedMetadata) throws Exception {
		new StrictExpectations() {{
			future.get();
			times = 1;
			result = expectedMetadata;
		}};
		final RecordMetadata actualMetadata = service.resolveFuture(future);
		assertEquals(expectedMetadata, actualMetadata);
	}
}
