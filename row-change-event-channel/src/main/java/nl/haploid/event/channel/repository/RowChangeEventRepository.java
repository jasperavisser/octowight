package nl.haploid.event.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RowChangeEventRepository extends JpaRepository<RowChangeEvent, Long> {
}
