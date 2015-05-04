package nl.haploid.octowight.registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VersionDmoRepository extends JpaRepository<VersionDmo, Long> {

	@Query(nativeQuery = true, value = "select nextval('octowight.resource_version_sequence') as version")
	VersionDmo findNext();
}
