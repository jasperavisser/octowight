package nl.haploid.octowight.channel.scout.service

import java.util

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.channel.scout.detector.ResourceDetector
import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceDetectorsService {
  @Autowired private[this] val _detectors: util.List[ResourceDetector] = null

  def detectors: Set[ResourceDetector] = _detectors.asScala.toSet

  def detectorsForAtomCategory(atomCategory: String): Set[ResourceDetector] =
    detectors.filter(_.atomCategories.contains(atomCategory))

  def detectResources(atomGroup: AtomGroup, atomIds: Set[Long]): Set[ResourceRoot] =
    detectorsForAtomCategory(atomGroup.category).flatMap(_.detect(atomGroup, atomIds))
}
