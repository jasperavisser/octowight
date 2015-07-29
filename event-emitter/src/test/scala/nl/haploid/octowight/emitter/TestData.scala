package nl.haploid.octowight.emitter

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent

object TestData {

  val AtomCategory = nextString

  val AtomOrigin = nextString

  def atomChangeEvent = AtomChangeEvent(id = nextLong, origin = AtomOrigin, category = AtomCategory)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString
}
