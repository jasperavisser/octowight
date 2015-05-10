package nl.haploid.octowight.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot

trait ResourceDetector {

  def getAtomTypes: List[String]

  def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot]
}
