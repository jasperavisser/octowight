package nl.haploid.octowight.registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceElementDmoRepository extends JpaRepository<ResourceElementDmo, Long> {

	ResourceElementDmo findByAtomIdAndAtomTypeAndAtomLocus
			(final Long atomId, final String atomType, final String atomLocus);

	List<ResourceElementDmo> findByAtomIdInAndAtomTypeAndAtomLocus
			(final List<Long> atomId, final String atomType, final String atomLocus);

	Long deleteByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
