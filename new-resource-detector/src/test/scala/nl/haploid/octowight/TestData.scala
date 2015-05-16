package nl.haploid.octowight

import java.lang
import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}

object TestData {

  def nextLong: Long = new Random().nextLong

  def resourceRoot(resourceId: lang.Long) =
    new ResourceRoot(resourceId = resourceId, resourceType = nextString, root = atom, version = null)

  def atom = new Atom(nextLong, nextString, nextString)

  def topic = nextString

  def atomChangeEvent(atomCategory: String): AtomChangeEvent = atomChangeEvent(nextString, atomCategory)

  def atomChangeEvent(atomOrigin: String, atomCategory: String) =
    new AtomChangeEvent(id = nextLong, atomId = nextLong, atomOrigin = atomOrigin, atomCategory = atomCategory)

  def nextString = UUID.randomUUID.toString
}
