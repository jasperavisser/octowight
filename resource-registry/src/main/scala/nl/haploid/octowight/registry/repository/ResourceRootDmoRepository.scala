package nl.haploid.octowight.registry.repository

import java.{lang, util}

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceRootDmoRepository extends MongoRepository[ResourceRootDmo, lang.Long] {

  def findByResourceCollection(resourceCollection: String): util.List[ResourceRootDmo]

  def findByResourceCollectionAndRootIdAndRootCategoryAndRootOrigin(resourceCollection: String, atomId: lang.Long, atomCategory: String, atomOrigin: String): ResourceRootDmo

  def findByResourceCollectionAndTombstone(resourceCollection: String, tombstone: Boolean): util.List[ResourceRootDmo]

  def findByResourceCollectionAndResourceId(resourceCollection: String, resourceId: lang.Long): ResourceRootDmo
}
