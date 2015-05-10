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

  def getDetectors = detectors.asScala

  def getDetectorsForAtomType(atomType: String) = getDetectors.filter(_.getAtomTypes.contains(atomType))

  def detectResources(atomGroup: AtomGroup, events: Traversable[AtomChangeEvent]) = {
    getDetectorsForAtomType(atomGroup.getAtomType).flatMap(_.detect(events))
  }
}
