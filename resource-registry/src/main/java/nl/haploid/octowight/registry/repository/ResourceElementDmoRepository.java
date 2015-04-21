package nl.haploid.octowight.registry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceElementDmoRepository extends JpaRepository<ResourceElementDmo, Long> {

    ResourceElementDmo findByAtomIdAndAtomTypeAndAtomLocus
            (final Long atomId, final String atomType, final String atomLocus);
}
