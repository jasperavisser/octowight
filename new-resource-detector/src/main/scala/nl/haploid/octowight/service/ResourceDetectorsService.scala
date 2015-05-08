package nl.haploid.octowight.service

import java.util

import nl.haploid.octowight.detector.ResourceDetector
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceDetectorsService {
  @Autowired private val detectors: util.List[ResourceDetector] = null

  def getDetectors = detectors.asScala

  def getDetectorsForAtomType(atomType: String) = {
    getDetectors
      .filter(_.getAtomTypes.contains(atomType))
      .asJava
  }

  def detectResources(atomGroup: AtomGroup, events: util.List[AtomChangeEvent]) = {
    getDetectorsForAtomType(atomGroup.getAtomType)
      .asScala
      .flatMap(_.detect(events).asScala)
      .asJava
  }
}
