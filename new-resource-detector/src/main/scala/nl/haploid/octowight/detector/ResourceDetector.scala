package nl.haploid.octowight.detector

import java.util

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot

trait ResourceDetector {

  def getAtomTypes: util.Collection[String]

  def detect(events: util.List[AtomChangeEvent]): util.List[ResourceRoot]
}
