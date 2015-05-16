package nl.haploid.octowight.service

import java.util

import nl.haploid.octowight.detector.ResourceDetector
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceDetectorsService {
  @Autowired private[this] val detectors: util.List[ResourceDetector] = null

  def getDetectors: List[ResourceDetector] = detectors.asScala.toList

  def getDetectorsForAtomCategory(atomCategory: String) = getDetectors.filter(_.getAtomCategories.contains(atomCategory))

  def detectResources(atomGroup: AtomGroup, events: Traversable[AtomChangeEvent]) = {
    getDetectorsForAtomCategory(atomGroup.getCategory).flatMap(_.detect(events))
  }
}
