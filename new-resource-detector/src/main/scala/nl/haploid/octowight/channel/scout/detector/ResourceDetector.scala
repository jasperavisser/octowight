package nl.haploid.octowight.channel.scout.detector

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.registry.data.ResourceRoot

trait ResourceDetector {

  def atomCategories: Set[String]

  def detect(atomGroup: AtomGroup, atomIds: Set[Long]): Set[ResourceRoot]
}
