package nl.haploid.octowight.sample.repository

import java.lang

import org.springframework.data.jpa.repository.JpaRepository

trait RoleDmoRepository extends JpaRepository[RoleDmo, lang.Long]
