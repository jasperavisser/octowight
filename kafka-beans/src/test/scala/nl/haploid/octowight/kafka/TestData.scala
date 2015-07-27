package nl.haploid.octowight.kafka

import java.util.{Random, UUID}

object TestData {

  def nextLong = new Random().nextLong

  def nextString = UUID.randomUUID.toString
}
