package nl.haploid.octowight

import java.util.{Random, UUID}

object TestData {
  val ATOM_LOCUS = "sunnydale"
  val ATOM_TYPE = "spike"

  def atomChangeEvent = {
    val event = new AtomChangeEvent
    event.setAtomId(nextLong)
    event.setAtomOrigin(ATOM_LOCUS)
    event.setAtomType(ATOM_TYPE)
    event
  }

  def nextLong = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def message = nextString

  def topic = nextString
}
