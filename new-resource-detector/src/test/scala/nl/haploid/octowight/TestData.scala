package nl.haploid.octowight

import java.lang
import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.ResourceRoot

object TestData {

  def nextLong = new Random().nextLong

  def resourceRoot(resourceId: lang.Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType("olson")
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin("madison avenue")
    resourceRoot.setAtomType("draper")
    resourceRoot
  }

  def topic = nextString

  def atomChangeEvent(atomType: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin("everywhere")
    event.setAtomType(atomType)
    event
  }

  def nextString = UUID.randomUUID.toString
}
