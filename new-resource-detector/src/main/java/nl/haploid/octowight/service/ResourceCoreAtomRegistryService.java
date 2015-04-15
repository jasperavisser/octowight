package nl.haploid.octowight.service;

import nl.haploid.octowight.data.ResourceCoreAtom;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceCoreAtomRegistryService {

	protected static final String EXISTING_RESOURCE_KEY = "redis:resourceCoreAtoms";

	protected static final String SEQUENCE_KEY = "redis:nextId";

	@Autowired
	private StringRedisTemplate redis;

	public boolean isNewResource(final ResourceCoreAtom coreAtom) {
		final String key = coreAtom.key();
		return redis.boundHashOps(EXISTING_RESOURCE_KEY).get(key) == null;
	}

	public ResourceCoreAtom putNewResource(final ResourceCoreAtom coreAtom) {
		final String key = coreAtom.key();
		final ResourceCoreAtom registeredCoreAtom = new ResourceCoreAtom();
		BeanUtils.copyProperties(coreAtom, registeredCoreAtom);
		registeredCoreAtom.setResourceId(getNextId());
		redis.boundHashOps(EXISTING_RESOURCE_KEY).put(key, Long.toString(registeredCoreAtom.getResourceId()));
		return registeredCoreAtom;
	}

	protected long getNextId() {
		return redis.boundValueOps(SEQUENCE_KEY).increment(1);
	}
}
