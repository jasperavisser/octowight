package nl.haploid.event.channel.repository

import org.springframework.data.jpa.repository.JpaRepository

trait RowChangeEventRepository extends JpaRepository[RowChangeEvent, java.lang.Long] {
}
