package nl.haploid.octowight

import java.util.{Random, UUID}

object TestData {
  val ATOM_LOCUS = nextString
  val ATOM_TYPE = nextString

  def atomChangeEvent = {
    val event = new AtomChangeEvent
    event.setAtomId(nextLong)
    event.setAtomOrigin(ATOM_LOCUS)
    event.setAtomCategory(ATOM_TYPE)
    event
  }

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def message = nextString

  def topic = nextString
}
