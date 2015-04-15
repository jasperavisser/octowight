package nl.haploid.octowight.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.data.ResourceCoreAtom;
import org.junit.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.Assert.assertEquals;

public class ResourceCoreAtomRegistryServiceTest {

	@Tested
	private ResourceCoreAtomRegistryService service;

	@Injectable
	private StringRedisTemplate redis;

	@Injectable
	private JsonMapper jsonMapper;

	@Test
	public void testIsNewResource(final @Mocked HashOperations mockedHashOperations) {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final String expectedKey = "the other woman";
		final boolean expectedIncluded = true;
		new StrictExpectations(service, coreAtom) {{
			coreAtom.key();
			times = 1;
			result = expectedKey;
			redis.opsForHash();
			times = 1;
			result = mockedHashOperations;
			mockedHashOperations.get(anyString, expectedKey);
			times = 1;
			result = null;
		}};
		final boolean actualIncluded = service.isNewResource(coreAtom);
		assertEquals(expectedIncluded, actualIncluded);
	}

	@Test
	public void testPutNewResource(final @Mocked HashOperations mockedHashOperations) {
		final ResourceCoreAtom coreAtom = TestData.resourceCoreAtom(null);
		final String expectedKey = "maidenform";
		final Long expectedId = 123l;
		new StrictExpectations(service, coreAtom) {{
			service.getNextResourceId();
			times = 1;
			result = expectedId;
			coreAtom.key();
			times = 1;
			result = expectedKey;
			redis.opsForHash();
			times = 1;
			result = mockedHashOperations;
			mockedHashOperations.put(anyString, expectedKey, expectedId.toString());
			times = 1;
			mockedHashOperations.put(anyString, expectedId.toString(), expectedKey);
			times = 1;
		}};
		final ResourceCoreAtom actualCoreAtom = service.putNewResource(coreAtom);
		assertEquals(expectedId, actualCoreAtom.getResourceId());
	}

	@Test
	public void testGetNextId(final @Mocked ValueOperations mockedValueOperations) {
		final long expectedId = 123l;
		new StrictExpectations() {{
			redis.opsForValue();
			times = 1;
			result = mockedValueOperations;
			mockedValueOperations.increment(anyString, 1);
			times = 1;
			result = expectedId;
		}};
		final long actualId = service.getNextResourceId();
		assertEquals(expectedId, actualId);
	}
}
