package nl.haploid.octowight

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.ResourceRoot

object TestData {

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin(nextString)
    resourceRoot.setAtomType(nextString)
    resourceRoot
  }

  def message = nextString

  def topic = nextString

  def atomChangeEvent(atomType: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(nextString)
    event.setAtomType(atomType)
    event
  }

  def nextString = UUID.randomUUID.toString
}
