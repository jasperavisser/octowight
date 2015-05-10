package nl.haploid.octowight.sample

import java.util.{Random, UUID}

import nl.haploid.octowight.sample.repository.AtomChangeEventDmo

object TestData {
  val AtomLocus = "sunnydale"
  val AtomType = "spike"

  def nextLong: Long = new Random().nextLong

  def atomChangeEventDmo = {
    val event = new AtomChangeEventDmo
    event.setAtomId(nextLong)
    event.setAtomOrigin(AtomLocus)
    event.setAtomType(AtomType)
    event
  }

  def topic = UUID.randomUUID.toString
}
