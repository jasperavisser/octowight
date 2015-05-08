package nl.haploid.octowight.registry.repository

import java.{lang, util}

import org.springframework.data.mongodb.repository.MongoRepository

// TODO: minimize use of java.Long
trait ResourceRootDmoRepository extends MongoRepository[ResourceRootDmo, lang.Long] {

  def findByResourceTypeAndAtomIdAndAtomTypeAndAtomOrigin(resourceType: String, atomId: lang.Long, atomType: String, atomOrigin: String): ResourceRootDmo

  def findByResourceType(resourceType: String): util.List[ResourceRootDmo]

  def findByResourceTypeAndResourceId(resourceType: String, resourceId: lang.Long): ResourceRootDmo
}
