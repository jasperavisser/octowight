package nl.haploid.resource.detector.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.event.JsonMapper;
import nl.haploid.resource.detector.TestData;
import org.junit.Test;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertEquals;

public class ResourceRegistryServiceTest {

	@Tested
	private ResourceRegistryService service;

	@Injectable
	private RedisTemplate<String, String> redis;

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	public void testIsNewResource(final @Mocked BoundHashOperations mockedBoundHashOperations) {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(null);
		final String expectedKey = "the other woman";
		final boolean expectedIncluded = true;
		new StrictExpectations(service, descriptor) {{
			descriptor.getKey();
			times = 1;
			result = expectedKey;
			redis.boundHashOps(anyString);
			times = 1;
			result = mockedBoundHashOperations;
			mockedBoundHashOperations.get(expectedKey);
			times = 1;
			result = null;
		}};
		final boolean actualIncluded = service.isNewResource(descriptor);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testRegisterNewResource(final @Mocked BoundHashOperations mockedBoundHashOperations) {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(null);
		final String expectedKey = "maidenform";
		final Long expectedId = 123l;
		new StrictExpectations(service, descriptor) {{
			descriptor.getKey();
			times = 1;
			result = expectedKey;
			service.getNextId();
			times = 1;
			result = expectedId;
			redis.boundHashOps(anyString);
			times = 1;
			result = mockedBoundHashOperations;
			mockedBoundHashOperations.put(expectedKey, expectedId.toString());
			times = 1;
		}};
		final ResourceDescriptor actualDescriptor = service.registerNewResource(descriptor);
		assertEquals(expectedId, actualDescriptor.getResourceId());
	}

	@Test
	public void testGetNextId(final @Mocked BoundValueOperations mockedBoundValueOperations) {
		final long expectedId = 123l;
		new StrictExpectations() {{
			redis.boundValueOps(anyString);
			times = 1;
			result = mockedBoundValueOperations;
			mockedBoundValueOperations.increment(1);
			times = 1;
			result = expectedId;
		}};
		final long actualId = service.getNextId();
		assertEquals(expectedId, actualId);
	}
}
