package nl.haploid.octowight.sample.detector

import java.lang.Long
import java.util
import java.util.Collections

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.detector.ResourceDetector
import nl.haploid.octowight.registry.data.ResourceRootFactory
import nl.haploid.octowight.sample.repository.{RoleDmo, RoleDmoRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConverters._

object CaptainResourceDetector {
  val RoleType = "captain"
  val ResourceType = "captain"
}

@Component
class CaptainResourceDetector extends ResourceDetector {
  @Autowired protected val roleDmoRepository: RoleDmoRepository = null
  @Autowired protected val resourceRootFactory: ResourceRootFactory = null

  override def getAtomTypes = Collections.singletonList(RoleDmo.AtomType)

  @Transactional(readOnly = true)
  override def detect(events: util.List[AtomChangeEvent]) = {
    val rolesById = getRolesById(events)
    events.asScala
      .filter(event => {
      rolesById.get(event.getAtomId) match {
        case Some(roleDmo) => isCaptain(roleDmo)
        case None => false
      }
    })
      .map(resourceRootFactory.fromAtomChangeEvent(_, CaptainResourceDetector.ResourceType))
      .toList
      .asJava
  }

  def getRolesById(events: util.List[AtomChangeEvent]) = {
    val roleIds: util.List[Long] = events.asScala
      .map(_.getAtomId)
      .toList
      .asJava
    roleDmoRepository.findAll(roleIds)
      .asScala
      .map(roleDmo => roleDmo.getAtomId -> roleDmo)
      .toMap
  }

  def isCaptain(roleDmo: RoleDmo) = {
    CaptainResourceDetector.RoleType == roleDmo.getName
  }
}
