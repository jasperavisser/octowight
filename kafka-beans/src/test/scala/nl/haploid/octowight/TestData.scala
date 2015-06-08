package nl.haploid.octowight

import java.util.{Random, UUID}

object TestData {

  def nextLong = new Random().nextLong

  def nextString = UUID.randomUUID.toString
}
