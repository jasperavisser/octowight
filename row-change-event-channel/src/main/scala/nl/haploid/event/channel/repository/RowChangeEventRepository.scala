package nl.haploid.event.channel.repository

import org.springframework.data.jpa.repository.JpaRepository

import javax.persistence.Entity
import javax.persistence.Table

trait RowChangeEventRepository extends JpaRepository[RowChangeEvent, java.lang.Long] {
}
