package nl.haploid.octowight.registry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceElementDmoRepository extends MongoRepository<ResourceElementDmo, String> {

	ResourceElementDmo findByAtomIdAndAtomTypeAndAtomLocus
			(final Long atomId, final String atomType, final String atomLocus);

	List<ResourceElementDmo> findByAtomIdInAndAtomTypeAndAtomLocus
			(final List<Long> atomId, final String atomType, final String atomLocus);

	Long deleteByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
