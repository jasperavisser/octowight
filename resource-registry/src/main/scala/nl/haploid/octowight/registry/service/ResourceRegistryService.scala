package nl.haploid.octowight.registry.service

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
    val resourceElementDmos = resourceElementDmoRepository.findByAtomIdInAndAtomTypeAndAtomOrigin(atomIds.asJava, atomGroup.getAtomType, atomGroup.getAtomOrigin)
    resourceElementDmos.asScala
      .flatMap(element => getResourceRootDmo(element))
      .map(markResourceDirty)
  }

  private[this] def getResourceRootDmo(resourceRoot: ResourceRoot): ResourceRootDmo = {
    resourceRootDmoRepository.findByResourceTypeAndAtomIdAndAtomTypeAndAtomOrigin(
      resourceRoot.getResourceType, resourceRoot.getAtomId, resourceRoot.getAtomType, resourceRoot.getAtomOrigin)
  }

  private[this] def getResourceRootDmo(resourceElementDmo: ResourceElementDmo) = {
    Option(resourceRootDmoRepository.findByResourceTypeAndResourceId(
      resourceElementDmo.getResourceType, resourceElementDmo.getResourceId))
  }

  private[this] def markResourceDirty(resourceRootDmo: ResourceRootDmo) = {
    log.info(s"Mark ${resourceRootDmo.getResourceType}/${resourceRootDmo.getResourceId} as dirty")
    val version = sequenceService.getNextValue(ResourceRootDmo.VersionSequence)
    resourceRootDmo.setVersion(version)
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmo))
  }

  private[this] def saveNewResource(resourceRoot: ResourceRoot): ResourceRoot = {
    val resourceRootDmo = ResourceRootDmo(resourceRoot)
    resourceRootDmo.setResourceId(sequenceService.getNextValue(ResourceRootDmo.IdSequence))
    resourceRootDmo.setVersion(sequenceService.getNextValue(ResourceRootDmo.VersionSequence))
    log.info(s"Save resource ${resourceRootDmo.getResourceType}/${resourceRootDmo.getResourceId}")
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmo))
  }

  private[this] def untombstoneResource(resourceRootDmo: ResourceRootDmo): ResourceRoot = {
    log.info(s"Remove tombstone for ${resourceRootDmo.getResourceType}/${resourceRootDmo.getResourceId}")
    resourceRootDmo.tombstone = false
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmo))
  }
}
