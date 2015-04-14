package nl.haploid.octowight.service;

import nl.haploid.octowight.TestData;
import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.data.ResourceCoreAtom;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceCoreAtomRegistryServiceIT extends AbstractIT {

	private static final long INITIAL_ID = 789l;

	@Autowired
	private RedisTemplate<String, String> redis;

	@Autowired
	private ResourceCoreAtomRegistryService service;

	@Before
	public void setup() {
		redis.boundValueOps(ResourceCoreAtomRegistryService.SEQUENCE_KEY).set(Long.toString(INITIAL_ID));
		redis.delete(ResourceCoreAtomRegistryService.EXISTING_RESOURCE_KEY);
	}

	@Test
	public void testIsNewResourceExcluded() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(TestData.nextLong());
		final String key = coreAtom.key();
		redis.boundHashOps(ResourceCoreAtomRegistryService.EXISTING_RESOURCE_KEY).put(key, coreAtom.getResourceId().toString());
		final boolean expectedIncluded = false;
		final boolean actualIncluded = service.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testIsNewResourceIncluded() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(TestData.nextLong());
		final boolean expectedIncluded = true;
		final boolean actualIncluded = service.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testPutNewResource() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final Long expectedId = INITIAL_ID + 1;
		final ResourceCoreAtom actualCoreAtom = service.putNewResource(coreAtom);
		final String key = coreAtom.key();
		assertNotNull(actualCoreAtom);
		assertEquals(expectedId, actualCoreAtom.getResourceId());
		final String value = redis.<String, String>boundHashOps(ResourceCoreAtomRegistryService.EXISTING_RESOURCE_KEY).get(key);
		assertNotNull(value);
	}

	@Test
	public void testGetNextId() {
		final long expectedId = INITIAL_ID + 1;
		final long actualId = service.getNextId();
		assertEquals(expectedId, actualId);
	}
}
