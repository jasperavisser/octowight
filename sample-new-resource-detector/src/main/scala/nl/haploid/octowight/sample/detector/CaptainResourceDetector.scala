package nl.haploid.octowight.sample.detector

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.detector.ResourceDetector
import nl.haploid.octowight.registry.data.ResourceRoot
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

  override def getAtomCategories = List(RoleDmo.AtomCategory)

  @Transactional(readOnly = true)
  override def detect(events: Traversable[AtomChangeEvent]): Traversable[ResourceRoot] = {
    val rolesById = getRolesById(events)
    events
      .filter(event => {
      rolesById.get(event.atomId) match {
        case Some(roleDmo) => isCaptain(roleDmo)
        case None => false
      }
    })
      .map(ResourceRoot(_, CaptainResourceDetector.ResourceType))
  }

  def getRolesById(events: Traversable[AtomChangeEvent]) = {
    val roleIds = events.map(_.atomId).toList
    roleDmoRepository.findAll(roleIds.asJava)
      .asScala
      .map(roleDmo => roleDmo.getId -> roleDmo)
      .toMap
  }

  def isCaptain(roleDmo: RoleDmo) = {
    CaptainResourceDetector.RoleType == roleDmo.getName
  }
}
