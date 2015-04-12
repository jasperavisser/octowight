package nl.haploid.resource.detector.service;

import nl.haploid.event.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

    public static final String EXISTING_RESOURCE_KEY = "redis:existingResources";

    public static final String SEQUENCE_KEY = "redis:nextId";

    @Autowired
    private RedisTemplate<String, String> redis;

    @Autowired
    private JsonMapper jsonMapper;

    // TODO: unit test/IT
    public boolean filterResource(final ResourceDescriptor descriptor) {
        final String key = createResourceKey(descriptor);
        return redis.<String, String>boundHashOps(EXISTING_RESOURCE_KEY).get(key) != null;
    }

    // TODO: unit test/IT
    public ResourceDescriptor registerResource(final ResourceDescriptor descriptor) {
        final String key = createResourceKey(descriptor);
        descriptor.setResourceId(getNextId());
        redis.boundHashOps(EXISTING_RESOURCE_KEY).put(key, descriptor.getResourceId());
        return descriptor;
    }

    // TODO: unit test/IT
    protected long getNextId() {
        return redis.boundValueOps(SEQUENCE_KEY).increment(1);
    }

    // TODO: unit test/IT
    protected String createResourceKey(final ResourceDescriptor descriptor) {
        // TODO: key must be very strong; cannot change with different JSON formatting or something
        return jsonMapper.toString(descriptor);
    }
}
