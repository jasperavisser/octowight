package nl.haploid.octowight

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceType = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)

  def message = nextString

  def topic = nextString

  def atomChangeEvent(atomCategory: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(nextString)
    event.setAtomCategory(atomCategory)
    event
  }

  def nextString = UUID.randomUUID.toString
}
