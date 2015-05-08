package nl.haploid.octowight.sample.repository

import java.lang

import org.springframework.data.jpa.repository.JpaRepository

trait AtomChangeEventDmoRepository extends JpaRepository[AtomChangeEventDmo, lang.Long] {
}
