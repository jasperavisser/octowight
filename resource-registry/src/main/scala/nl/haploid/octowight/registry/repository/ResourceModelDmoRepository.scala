package nl.haploid.octowight.registry.repository

import java.lang

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceModelDmoRepository extends MongoRepository[ResourceModelDmo, ResourceModelId] {

  def findByIdAndVersion(id: ResourceModelId, version: lang.Long): ResourceModelDmo
}
