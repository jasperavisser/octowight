package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo

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
