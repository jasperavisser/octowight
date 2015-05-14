package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceModelDmoRepository extends MongoRepository[ResourceModelDmo, ResourceModelDmoId] {

  def findByIdAndVersion(id: ResourceModelDmoId, version: lang.Long): ResourceModelDmo
}
