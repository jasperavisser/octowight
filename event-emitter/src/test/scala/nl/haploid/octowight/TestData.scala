package nl.haploid.octowight

import java.util.{Random, UUID}

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
