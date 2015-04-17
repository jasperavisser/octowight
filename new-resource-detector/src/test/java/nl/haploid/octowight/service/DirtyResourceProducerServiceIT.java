package nl.haploid.octowight.service;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.registry.data.Resource;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Future;

import static org.junit.Assert.assertNotNull;

public class DirtyResourceProducerServiceIT extends AbstractIT {

	@Autowired
	private DirtyResourceProducerService service;

	@Test
	public void testSendDirtyResource() throws Exception {
		final Resource resource = TestData.resource(555l);
		final Future<RecordMetadata> future = service.sendDirtyResource(resource);
		assertNotNull(future.get());
	}
}
