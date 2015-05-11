package nl.haploid.octowight.sample

import java.lang
import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.registry.repository.{ResourceModelId, ResourceRootDmo}
import nl.haploid.octowight.sample.data.CaptainModel
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

object TestData {
  def personDmo: PersonDmo = personDmo(null)

  def personDmo(id: lang.Long) = {
    val person = new PersonDmo
    person.setId(id)
    person.setName(nextString)
    person
  }

  def nextString = UUID.randomUUID.toString

  def nextLong: Long = new Random().nextLong

  // TODO: maybe return mock[ResourceModelId] so we don't accidentally have equivalents
  def resourceModelId : ResourceModelId = new ResourceModelId

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin(nextString)
    resourceRoot.setAtomType(nextString)
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType(nextString)
    resourceRoot
  }

  def captainModel = new CaptainModel

  def resourceRootDmo = {
    val resourceRootDmo = new ResourceRootDmo
    resourceRootDmo.setResourceId(nextLong)
    resourceRootDmo
  }

  def roleDmo(personDmo: PersonDmo, name: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setName(name)
    dmo
  }
}
