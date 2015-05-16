package nl.haploid.octowight

import java.lang
import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: lang.Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setRoot(new Atom(nextLong, nextString, nextString))
    resourceRoot
  }

  def topic = nextString

  def atomChangeEvent(atomCategory: String): AtomChangeEvent = atomChangeEvent(nextString, atomCategory)

  def atomChangeEvent(atomOrigin: String, atomCategory: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(atomOrigin)
    event.setAtomCategory(atomCategory)
    event
  }

  def nextString = UUID.randomUUID.toString
}
