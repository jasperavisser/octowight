package nl.haploid.octowight.repository;

import nl.haploid.octowight.data.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: test!
public interface ResourceDmoRepository extends JpaRepository<ResourceDmo, Long> {

	ResourceDmo findByAtomIdAndAtomTypeAndAtomLocus(final Long atomId, final String atomType, final String atomLocus);

	Resource findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
