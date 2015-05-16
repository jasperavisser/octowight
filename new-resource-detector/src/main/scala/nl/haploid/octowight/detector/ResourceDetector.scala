package nl.haploid.octowight.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot

trait ResourceDetector {

  def getAtomCategories: List[String]

  def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot]
}
