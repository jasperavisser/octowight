package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

object TestData {

  def name = nextString

  def nextString = UUID.randomUUID.toString

  def nextLong: Long = new Random().nextLong

  def personDmo = {
    val dmo = new PersonDmo
    dmo.setId(nextLong)
    dmo.setName(name)
    dmo
  }

  def roleDmo(personDmo: PersonDmo, name: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setName(name)
    dmo
  }

  def resourceRoot = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setRoot(new Atom(nextLong, nextString, nextString))
    resourceRoot.setResourceId(nextLong)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setVersion(nextLong)
    resourceRoot
  }
}
