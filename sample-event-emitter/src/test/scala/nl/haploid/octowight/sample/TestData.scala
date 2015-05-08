package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo
import java.util.Random
import java.util.UUID

object TestData {
  val ATOM_LOCUS: String = "sunnydale"
  val ATOM_TYPE: String = "spike"

  def nextLong: Long = {
    return new Random().nextLong
  }

  def atomChangeEventDmo: AtomChangeEventDmo = {
    val event: AtomChangeEventDmo = new AtomChangeEventDmo
    event.setAtomId(nextLong)
    event.setAtomOrigin(ATOM_LOCUS)
    event.setAtomType(ATOM_TYPE)
    return event
  }

  def topic: String = {
    return UUID.randomUUID.toString
  }
}
