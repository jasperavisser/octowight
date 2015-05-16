package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo

object TestData {
  val AtomLocus = nextString
  val AtomCategory = nextString

  def nextLong: Long = new Random().nextLong

  def atomChangeEventDmo =
    new AtomChangeEventDmo(id = null, atomId = nextLong, atomOrigin = AtomLocus, atomCategory = AtomCategory)

  def topic = nextString

  def nextString = UUID.randomUUID.toString
}
