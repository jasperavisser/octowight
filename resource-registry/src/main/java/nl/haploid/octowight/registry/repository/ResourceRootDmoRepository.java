package nl.haploid.octowight.registry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceRootDmoRepository extends MongoRepository<ResourceRootDmo, Long> {

	ResourceRootDmo findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus
			(final String resourceType, final Long atomId, final String atomType, final String atomLocus);

	List<ResourceRootDmo> findByResourceType(final String resourceType);

	ResourceRootDmo findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
