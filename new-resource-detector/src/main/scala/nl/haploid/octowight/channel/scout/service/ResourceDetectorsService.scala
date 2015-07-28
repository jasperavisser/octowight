package nl.haploid.octowight.channel.scout.service

import java.util

import nl.haploid.octowight.channel.scout.detector.ResourceDetector
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceDetectorsService {
  @Autowired private[this] val _detectors: util.List[ResourceDetector] = null

  def detectors = _detectors.asScala.toList

  def detectorsForAtomCategory(atomCategory: String) =
    detectors.filter(_.atomCategories.contains(atomCategory))

  def detectResources(atomGroup: AtomGroup, events: Traversable[AtomChangeEvent]) =
    detectorsForAtomCategory(atomGroup.category).flatMap(_.detect(events))
}
