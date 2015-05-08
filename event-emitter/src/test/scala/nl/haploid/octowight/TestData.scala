package nl.haploid.octowight

import java.util.{Random, UUID}

object TestData {
  val ATOM_LOCUS: String = "sunnydale"
  val ATOM_TYPE: String = "spike"

  def atomChangeEvent: AtomChangeEvent = {
    val event: AtomChangeEvent = new AtomChangeEvent
    event.setAtomId(nextLong)
    event.setAtomOrigin(ATOM_LOCUS)
    event.setAtomType(ATOM_TYPE)
    event
  }

  def nextLong: Long = new Random().nextLong

  def nextString: String = UUID.randomUUID.toString

  def message: String = nextString

  def topic: String = nextString
}
