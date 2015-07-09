package nl.haploid.octowight.sample.repository

import java.lang

import org.springframework.data.mongodb.repository.MongoRepository

trait CaptainDmoRepository extends MongoRepository[CaptainDmo, lang.Long]
