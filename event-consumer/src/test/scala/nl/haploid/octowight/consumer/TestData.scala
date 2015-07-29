package nl.haploid.octowight.consumer

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def atom = new Atom(nextLong, nextString, nextString)

  def atomChangeEvent(atomCategory: String) = AtomChangeEvent(id = nextLong, origin = nextString, category = atomCategory)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)
}
