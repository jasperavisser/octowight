package nl.haploid.octowight.registry.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResourceElementDmoRepository extends MongoRepository<ResourceElementDmo, String> {

	ResourceElementDmo findByAtomIdAndAtomTypeAndAtomOrigin
			(final Long atomId, final String atomType, final String atomOrigin);

	List<ResourceElementDmo> findByAtomIdInAndAtomTypeAndAtomOrigin
			(final List<Long> atomId, final String atomType, final String atomOrigin);

	Long deleteByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
