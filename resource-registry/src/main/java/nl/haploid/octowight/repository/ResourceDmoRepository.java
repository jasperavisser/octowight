package nl.haploid.octowight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceDmoRepository extends JpaRepository<ResourceDmo, Long> {

	ResourceDmo findByResourceTypeAndAtomIdAndAtomTypeAndAtomLocus
			(final String resourceType, final Long atomId, final String atomType, final String atomLocus);

	List<ResourceDmo> findByResourceType(final String resourceType);

	ResourceDmo findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
