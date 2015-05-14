package nl.haploid.octowight.registry.repository

import java.{lang, util}

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceRootDmoRepository extends MongoRepository[ResourceRootDmo, lang.Long] {

  def findByResourceType(resourceType: String): util.List[ResourceRootDmo]

  def findByResourceTypeAndAtomIdAndAtomTypeAndAtomOrigin(resourceType: String, atomId: lang.Long, atomType: String, atomOrigin: String): ResourceRootDmo

  def findByResourceTypeAndTombstone(resourceType: String, tombstone: Boolean): util.List[ResourceRootDmo]

  def findByResourceTypeAndResourceId(resourceType: String, resourceId: lang.Long): ResourceRootDmo
}
