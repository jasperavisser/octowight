package nl.haploid.octowight.registry.repository

import java.{lang, util}

import org.springframework.data.mongodb.repository.MongoRepository

trait ResourceElementDmoRepository extends MongoRepository[ResourceElementDmo, String] {

  def findByAtomIdAndAtomCategoryAndAtomOrigin(atomId: lang.Long, atomCategory: String, atomOrigin: String): ResourceElementDmo

  def findByAtomIdInAndAtomCategoryAndAtomOrigin(atomId: lang.Iterable[lang.Long], atomCategory: String, atomOrigin: String): util.List[ResourceElementDmo]

  def deleteByResourceTypeAndResourceId(resourceType: String, resourceId: lang.Long): Long
}
