package nl.haploid.octowight.emitter

import java.util.{Random, UUID}

import nl.haploid.octowight.AtomChangeEvent

object TestData {
  val AtomCategory = nextString
  val AtomOrigin = nextString

  def atomChangeEvent =
    new AtomChangeEvent(id = nextLong, atomId = nextLong, atomOrigin = AtomOrigin, atomCategory = AtomCategory)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def message = nextString

  def topic = nextString
}
