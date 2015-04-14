package nl.haploid.octowight.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceRegistryService {

	protected static final String EXISTING_RESOURCE_KEY = "redis:existingResources";

	protected static final String SEQUENCE_KEY = "redis:nextId";

	@Autowired
	private RedisTemplate<String, String> redis;

	public boolean isNewResource(final ResourceDescriptor descriptor) {
		final String key = descriptor.getKey();
		return redis.boundHashOps(EXISTING_RESOURCE_KEY).get(key) == null;
	}

	public ResourceDescriptor registerNewResource(final ResourceDescriptor descriptor) {
		final String key = descriptor.getKey();
		final ResourceDescriptor registeredDescriptor = new ResourceDescriptor();
		BeanUtils.copyProperties(descriptor, registeredDescriptor);
		registeredDescriptor.setResourceId(getNextId());
		redis.boundHashOps(EXISTING_RESOURCE_KEY).put(key, Long.toString(registeredDescriptor.getResourceId()));
		return registeredDescriptor;
	}

	protected long getNextId() {
		return redis.boundValueOps(SEQUENCE_KEY).increment(1);
	}
}
