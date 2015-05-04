package nl.haploid.octowight.registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceModelDmoRepository extends JpaRepository<ResourceModelDmo, Long> {

	ResourceModelDmo findByResourceTypeAndResourceId(final String type, final long id);
}
