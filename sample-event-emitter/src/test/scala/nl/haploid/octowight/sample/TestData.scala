package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo
import java.util.Random
import java.util.UUID

object TestData {
  val ATOM_LOCUS = "sunnydale"
  val ATOM_TYPE = "spike"

  def nextLong = new Random().nextLong

  def atomChangeEventDmo = {
    val event = new AtomChangeEventDmo
    event.setAtomId(nextLong)
    event.setAtomOrigin(ATOM_LOCUS)
    event.setAtomType(ATOM_TYPE)
    event
  }

  def topic = UUID.randomUUID.toString
}
