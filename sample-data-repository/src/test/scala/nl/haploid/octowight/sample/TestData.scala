package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

object TestData {

  def name = nextString

  def nextString = UUID.randomUUID.toString

  def nextLong = new Random().nextLong

  def personDmo = {
    val dmo = new PersonDmo
    dmo.setId(nextLong)
    dmo.setName(name)
    dmo
  }

  def roleDmo(personDmo: PersonDmo, `type`: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setType(`type`)
    dmo
  }

  def resourceRoot = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin(nextString)
    resourceRoot.setAtomType(nextString)
    resourceRoot.setResourceId(nextLong)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setVersion(nextLong)
    resourceRoot
  }
}
