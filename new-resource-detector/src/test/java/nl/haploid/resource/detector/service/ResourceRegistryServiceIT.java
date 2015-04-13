package nl.haploid.resource.detector.service;

import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceRegistryServiceIT extends AbstractIT {

	private static final long INITIAL_ID = 789l;

	@Autowired
	private RedisTemplate<String, String> redis;

	@Autowired
	private ResourceRegistryService service;

	@Before
	public void setup() {
		redis.boundValueOps(ResourceRegistryService.SEQUENCE_KEY).set(Long.toString(INITIAL_ID));
		redis.delete(ResourceRegistryService.EXISTING_RESOURCE_KEY);
	}

	@Test
	public void testIsNewResourceExcluded() {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(TestData.nextLong());
		final String key = descriptor.getKey();
		redis.boundHashOps(ResourceRegistryService.EXISTING_RESOURCE_KEY).put(key, descriptor.getResourceId().toString());
		final boolean expectedIncluded = false;
		final boolean actualIncluded = service.isNewResource(descriptor);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testIsNewResourceIncluded() {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(TestData.nextLong());
		final boolean expectedIncluded = true;
		final boolean actualIncluded = service.isNewResource(descriptor);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testRegisterNewResource() {
		final ResourceDescriptor descriptor = TestData.resourceDescriptor(null);
		final Long expectedId = INITIAL_ID + 1;
		final ResourceDescriptor actualDescriptor = service.registerNewResource(descriptor);
		final String key = descriptor.getKey();
		assertNotNull(actualDescriptor);
		assertEquals(expectedId, actualDescriptor.getResourceId());
		final String value = redis.<String, String>boundHashOps(ResourceRegistryService.EXISTING_RESOURCE_KEY).get(key);
		assertNotNull(value);
	}

	@Test
	public void testGetNextId() {
		final long expectedId = INITIAL_ID + 1;
		final long actualId = service.getNextId();
		assertEquals(expectedId, actualId);
	}
}
