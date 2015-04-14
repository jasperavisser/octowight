package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.data.ResourceCoreAtom;
import org.junit.Test;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.Assert.assertEquals;

public class ResourceCoreAtomRegistryServiceTest {

	@Tested
	private ResourceCoreAtomRegistryService service;

	@Injectable
	private RedisTemplate<String, String> redis;

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	public void testIsNewResource(final @Mocked BoundHashOperations mockedBoundHashOperations) {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final String expectedKey = "the other woman";
		final boolean expectedIncluded = true;
		new StrictExpectations(service, coreAtom) {{
			coreAtom.key();
			times = 1;
			result = expectedKey;
			redis.boundHashOps(anyString);
			times = 1;
			result = mockedBoundHashOperations;
			mockedBoundHashOperations.get(expectedKey);
			times = 1;
			result = null;
		}};
		final boolean actualIncluded = service.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testPutNewResource(final @Mocked BoundHashOperations mockedBoundHashOperations) {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final String expectedKey = "maidenform";
		final Long expectedId = 123l;
		new StrictExpectations(service, coreAtom) {{
			coreAtom.key();
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
		final ResourceCoreAtom actualCoreAtom = service.putNewResource(coreAtom);
		assertEquals(expectedId, actualCoreAtom.getResourceId());
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
