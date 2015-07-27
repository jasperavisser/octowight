package nl.haploid.octowight.consumer

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)

  def message = nextString

  def topic = nextString

  def atomChangeEvent(atomCategory: String) =
    new AtomChangeEvent(id = nextLong, atomId = nextLong, atomOrigin = nextString, atomCategory = atomCategory)

  def nextString = UUID.randomUUID.toString
}
