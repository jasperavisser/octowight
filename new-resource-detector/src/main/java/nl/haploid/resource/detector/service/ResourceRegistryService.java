package nl.haploid.resource.detector.service;

import nl.haploid.event.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

    protected static final String EXISTING_RESOURCE_KEY = "redis:existingResources";

    protected static final String SEQUENCE_KEY = "redis:nextId";

    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    private JsonMapper jsonMapper;

    public boolean excludeExistingResources(final ResourceDescriptor descriptor) {
        final String key = createResourceKey(descriptor);
        return redis.boundHashOps(EXISTING_RESOURCE_KEY).get(key) == null;
    }

    public ResourceDescriptor registerNewResource(final ResourceDescriptor descriptor) {
        // TODO: clone descriptor before mutating it
        final String key = createResourceKey(descriptor);
        descriptor.setResourceId(getNextId());
        redis.boundHashOps(EXISTING_RESOURCE_KEY).put(key, Long.toString(descriptor.getResourceId()));
        return descriptor;
    }

    protected long getNextId() {
        return redis.boundValueOps(SEQUENCE_KEY).increment(1);
    }

    protected String createResourceKey(final ResourceDescriptor descriptor) {
        // TODO: key must be very strong; cannot change with different JSON formatting or something
        return jsonMapper.toString(descriptor);
    }
}
