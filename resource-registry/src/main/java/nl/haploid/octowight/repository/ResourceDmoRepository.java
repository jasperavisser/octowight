package nl.haploid.octowight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// TODO: test!
public interface ResourceDmoRepository extends JpaRepository<ResourceDmo, Long> {

	ResourceDmo findByAtomIdAndAtomTypeAndAtomLocus(final Long atomId, final String atomType, final String atomLocus);

	List<ResourceDmo> findByResourceType(final String resourceType);

	ResourceDmo findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
