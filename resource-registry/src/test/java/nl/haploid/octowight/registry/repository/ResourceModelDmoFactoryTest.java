package nl.haploid.octowight.registry.repository;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.TestData;
import nl.haploid.octowight.registry.data.Resource;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceModelDmoFactoryTest {

	@Tested
	private ResourceModelDmoFactory resourceModelDmoFactory;

	@Injectable
	private ResourceModelIdFactory resourceModelIdFactory;

	@Test
	public void testFromResourceAndBody(final @Mocked Resource resource) {
		final ResourceModelId resourceModelId = TestData.resourceModelId();
		final String body = TestData.nextString();
		final Long version = TestData.nextLong();
		new StrictExpectations() {{
			resourceModelIdFactory.resourceModelId(resource);
			times = 1;
			result = resourceModelId;
			resource.getVersion();
			times = 1;
			result = version;
		}};
		final ResourceModelDmo actualResourceModelDmo = resourceModelDmoFactory.fromResourceAndBody(resource, body);
		assertEquals(resourceModelId, actualResourceModelDmo.getId());
		assertEquals(body, actualResourceModelDmo.getBody());
		assertEquals(version, actualResourceModelDmo.getVersion());
	}
}
