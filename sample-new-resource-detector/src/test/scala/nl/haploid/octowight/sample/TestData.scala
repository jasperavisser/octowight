package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}

object TestData {

  def atomChangeEvent: AtomChangeEvent = atomChangeEvent(nextLong)

  def atomChangeEvent(atomId: Long) =
    new AtomChangeEvent(id = nextLong, atomId = atomId, atomOrigin = nextString, atomCategory = PersonDmo.AtomCategory)

  def name = nextString

  def nextLong: Long = new Random().nextLong

  def personDmo: PersonDmo = personDmo(nextLong)

  def personDmo(id: Long) = {
    val dmo = new PersonDmo
    dmo.setId(id)
    dmo.setName(name)
    dmo
  }

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceType = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)

  def roleDmo(personDmo: PersonDmo, name: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setName(name)
    dmo
  }

  def topic = nextString

  def roleDmo(id: Long): RoleDmo = {
    val dmo = new RoleDmo
    dmo.setId(id)
    dmo
  }

  def nextString = UUID.randomUUID.toString
}
