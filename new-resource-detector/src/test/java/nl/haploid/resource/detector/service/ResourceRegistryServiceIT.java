package nl.haploid.resource.detector.service;

import junit.framework.Assert;
import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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
    public void testExcludeExistingResourcesExcluded() {
        final ResourceDescriptor descriptor = TestData.resourceDescriptor(TestData.nextLong());
        final String key = service.createResourceKey(descriptor);
        redis.boundHashOps(ResourceRegistryService.EXISTING_RESOURCE_KEY).put(key, descriptor.getResourceId().toString());
        final boolean expectedIncluded = false;
        final boolean actualIncluded = service.excludeExistingResources(descriptor);
        Assert.assertEquals(expectedIncluded, actualIncluded);
    }

    @Test
    public void testExcludeExistingResourcesIncluded() {
        final ResourceDescriptor descriptor = TestData.resourceDescriptor(TestData.nextLong());
        final boolean expectedIncluded = true;
        final boolean actualIncluded = service.excludeExistingResources(descriptor);
        Assert.assertEquals(expectedIncluded, actualIncluded);
    }

    @Test
    public void testRegisterNewResource() {
        final ResourceDescriptor descriptor = TestData.resourceDescriptor(null);
        final Long expectedId = INITIAL_ID + 1;
        final ResourceDescriptor actualDescriptor = service.registerNewResource(descriptor);
        final String key = service.createResourceKey(descriptor);
        Assert.assertNotNull(actualDescriptor);
        Assert.assertEquals(expectedId, actualDescriptor.getResourceId());
        final String value = redis.<String, String>boundHashOps(ResourceRegistryService.EXISTING_RESOURCE_KEY).get(key);
        Assert.assertNotNull(value);
    }

    @Test
    public void testGetNextId() {
        final long expectedId = INITIAL_ID + 1;
        final long actualId = service.getNextId();
        Assert.assertEquals(expectedId, actualId);
    }

    @Test
    public void testCreateResourceKey() {
        final ResourceDescriptor descriptor = TestData.resourceDescriptor(null);
        final String actualKey = service.createResourceKey(descriptor);
        Assert.assertNotNull(actualKey);
    }
}
