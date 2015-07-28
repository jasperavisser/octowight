package nl.haploid.octowight.channel.scout.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.data.ResourceRoot

trait ResourceDetector {

  def atomCategories: Set[String]

  def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot]
}
