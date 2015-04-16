package nl.haploid.octowight.repository;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.data.ResourceCoreAtom;
import nl.haploid.octowight.repository.ResourceRegistryRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ResourceRegistryRepositoryIT extends AbstractIT {

	private static final long INITIAL_ID = 789l;

	@Autowired
	private StringRedisTemplate redis;

	@Autowired
	private ResourceRegistryRepository repository;

	@Before
	public void setup() {
		redis.opsForValue().set(ResourceRegistryRepository.NEXT_RESOURCE_ID_KEY, Long.toString(INITIAL_ID));
		redis.delete(ResourceRegistryRepository.CORE_ATOM_TO_RESOURCE_KEY);
	}

	@Test
	public void testIsNewResourceExcluded() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(TestData.nextLong());
		final String key = coreAtom.key();
		redis.opsForHash().put(ResourceRegistryRepository.CORE_ATOM_TO_RESOURCE_KEY, key, coreAtom.getResourceId().toString());
		final boolean expectedIncluded = false;
		final boolean actualIncluded = repository.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testIsNewResourceIncluded() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(TestData.nextLong());
		final boolean expectedIncluded = true;
		final boolean actualIncluded = repository.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testPutNewResource() {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final Long expectedId = INITIAL_ID + 1;
		final ResourceCoreAtom actualCoreAtom = repository.putNewResource(coreAtom);
		final String key = coreAtom.key();
		assertNotNull(actualCoreAtom);
		assertEquals(expectedId, actualCoreAtom.getResourceId());
		final String value = redis.<String, String>opsForHash().get(ResourceRegistryRepository.CORE_ATOM_TO_RESOURCE_KEY, key);
		assertNotNull(value);
	}

	@Test
	public void testGetNextId() {
		final long expectedId = INITIAL_ID + 1;
		final long actualId = repository.getNextResourceId();
		assertEquals(expectedId, actualId);
	}
}
