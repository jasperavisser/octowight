package nl.haploid.octowight.registry.repository

import java.{lang, util}

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceElementDmoRepository extends MongoRepository[ResourceElementDmo, String] {

  def findByAtomIdAndAtomTypeAndAtomOrigin(atomId: lang.Long, atomType: String, atomOrigin: String): ResourceElementDmo

  def findByAtomIdInAndAtomTypeAndAtomOrigin(atomId: util.List[lang.Long], atomType: String, atomOrigin: String): util.List[ResourceElementDmo]

  def deleteByResourceTypeAndResourceId(resourceType: String, resourceId: lang.Long): Long
}
