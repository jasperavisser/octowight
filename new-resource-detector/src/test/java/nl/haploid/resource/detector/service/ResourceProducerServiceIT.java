package nl.haploid.resource.detector.service;

import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

import static org.junit.Assert.assertNotNull;

public class ResourceProducerServiceIT extends AbstractIT {

	@Autowired
	private ResourceProducerService service;

	@Test
	public void testPublishResourceDescriptor() throws Exception {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(555l);
		final Future<RecordMetadata> future = service.publishResourceDescriptor(descriptor);
		assertNotNull(future.get());
	}
}
