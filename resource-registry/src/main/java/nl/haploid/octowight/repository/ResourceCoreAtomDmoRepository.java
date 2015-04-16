package nl.haploid.octowight.repository;

import nl.haploid.octowight.data.ResourceCoreAtom;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO: test!
public interface ResourceCoreAtomDmoRepository extends JpaRepository<ResourceCoreAtomDmo, Long> {

	ResourceCoreAtomDmo findByAtomIdAndAtomTypeAndAtomLocus(final Long atomId, final String atomType, final String atomLocus);

	ResourceCoreAtom findByResourceTypeAndResourceId(final String resourceType, final long resourceId);
}
