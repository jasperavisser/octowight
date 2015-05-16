package nl.haploid.octowight.registry.service

import java.lang.Long

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceRegistryService {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val sequenceService: SequenceService = null

  def saveResource(resourceRoot: ResourceRoot): Option[ResourceRoot] = {
    Option(getResourceRootDmo(resourceRoot)) match {
      case Some(resourceRootDmo) => if (resourceRootDmo.tombstone) Some(untombstoneResource(resourceRootDmo)) else None
      case None => Some(saveNewResource(resourceRoot))
    }
  }

  def markResourcesDirty(atomGroup: AtomGroup, atomChangeEvents: Iterable[AtomChangeEvent]) = {
    val atomIds = atomChangeEvents.map(_.getAtomId)
    val resourceElementDmos = resourceElementDmoRepository.findByAtomIdInAndAtomCategoryAndAtomOrigin(atomIds.asJava, atomGroup.getCategory, atomGroup.getOrigin)
    resourceElementDmos.asScala
      .flatMap(element => getResourceRootDmo(element))
      .map(markResourceDirty)
  }

  private[this] def getResourceRootDmo(resourceRoot: ResourceRoot): ResourceRootDmo = {
    resourceRootDmoRepository.findByResourceTypeAndRootIdAndRootCategoryAndRootOrigin(
      resourceRoot.resourceType, resourceRoot.root.id, resourceRoot.root.category, resourceRoot.root.origin)
  }

  private[this] def getResourceRootDmo(resourceElementDmo: ResourceElementDmo) = {
    Option(resourceRootDmoRepository.findByResourceTypeAndResourceId(
      resourceElementDmo.resourceType, resourceElementDmo.resourceId))
  }

  private[this] def markResourceDirty(resourceRootDmo: ResourceRootDmo) = {
    log.info(s"Mark ${resourceRootDmo.resourceType}/${resourceRootDmo.resourceId} as dirty")
    val version = sequenceService.getNextValue(ResourceRootDmo.VersionSequence)
    val resourceRootDmoToSave = resourceRootDmo.copy(version = version)
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }

  private[this] def saveNewResource(resourceRoot: ResourceRoot): ResourceRoot = {
    val resourceId: Long = sequenceService.getNextValue(ResourceRootDmo.IdSequence)
    val version: Long = sequenceService.getNextValue(ResourceRootDmo.VersionSequence)
    val resourceRootDmoToSave = ResourceRootDmo(resourceRoot).copy(resourceId = resourceId, version = version)
    log.info(s"Save resource ${resourceRootDmoToSave.resourceType}/${resourceRootDmoToSave.resourceId}")
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }

  private[this] def untombstoneResource(resourceRootDmo: ResourceRootDmo): ResourceRoot = {
    log.info(s"Remove tombstone for ${resourceRootDmo.resourceType}/${resourceRootDmo.resourceId}")
    val resourceRootDmoToSave = resourceRootDmo.copy(tombstone = false)
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }
}
