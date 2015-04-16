package nl.haploid.octowight.repository;

import nl.haploid.octowight.data.ResourceCoreAtom;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceRegistryRepository {

	protected static final String CORE_ATOM_TO_RESOURCE_KEY = "redis:coreAtomToResource";

	protected static final String RESOURCE_TO_CORE_ATOM_KEY = "redis:resourceToCoreAtom";

	protected static final String NEXT_RESOURCE_ID_KEY = "redis:nextResourceId";

	@Autowired
	private StringRedisTemplate redis;

	public boolean isNewResource(final ResourceCoreAtom coreAtom) {
		final String key = coreAtom.key();
		return redis.opsForHash().get(CORE_ATOM_TO_RESOURCE_KEY, key) == null;
	}

	public ResourceCoreAtom putNewResource(final ResourceCoreAtom coreAtom) {
		final ResourceCoreAtom coreAtomClone = assignResourceId(coreAtom);
		final String coreAtomKey = coreAtom.key();
		final String resourceKey = Long.toString(coreAtomClone.getResourceId());
		final HashOperations<String, Object, Object> hashOps = redis.opsForHash();
		hashOps.put(CORE_ATOM_TO_RESOURCE_KEY, coreAtomKey, resourceKey);
		// TODO: key = resourceType + resourceId
		// TODO: maybe Postgres would be easier :)
		hashOps.put(RESOURCE_TO_CORE_ATOM_KEY, resourceKey, coreAtomKey);
		return coreAtomClone;
	}

	private ResourceCoreAtom assignResourceId(final ResourceCoreAtom coreAtom) {
		final ResourceCoreAtom coreAtomClone = new ResourceCoreAtom();
		BeanUtils.copyProperties(coreAtom, coreAtomClone);
		coreAtomClone.setResourceId(getNextResourceId());
		return coreAtomClone;
	}

	protected long getNextResourceId() {
		return redis.opsForValue().increment(NEXT_RESOURCE_ID_KEY, 1);
	}

	public ResourceCoreAtom findByResourceTypeAndResourceId(final String resourceType, final long resourceId) {
		return null; // TODO
	}
}
