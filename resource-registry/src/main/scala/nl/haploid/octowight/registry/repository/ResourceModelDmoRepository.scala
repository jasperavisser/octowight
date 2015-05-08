package nl.haploid.octowight.registry.repository

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceModelDmoRepository extends MongoRepository[ResourceModelDmo, ResourceModelId] {
}
