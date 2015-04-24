package nl.haploid.octowight.registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRootDmoRepository extends JpaRepository<ResourceRootDmo, Long> {

	ResourceRootDmo findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus
			(final String resourceType, final Long atomId, final String atomType, final String atomLocus);

	List<ResourceRootDmo> findByResourceType(final String resourceType);

	ResourceRootDmo findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
