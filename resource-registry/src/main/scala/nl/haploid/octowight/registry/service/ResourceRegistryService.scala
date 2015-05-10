package nl.haploid.octowight.registry.service

import nl.haploid.octowight.registry.data.{ResourceRoot, ResourceRootFactory}
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
  @Autowired private[this] val resourceRootFactory: ResourceRootFactory = null
  @Autowired private[this] val resourceRootDmoFactory: ResourceRootDmoFactory = null
  @Autowired private[this] val sequenceService: SequenceService = null

  def isNewResource(resourceRoot: ResourceRoot) = {
    val dmo = resourceRootDmoRepository.findByResourceTypeAndAtomIdAndAtomTypeAndAtomOrigin(resourceRoot.getResourceType, resourceRoot.getAtomId, resourceRoot.getAtomType, resourceRoot.getAtomOrigin)
    dmo == null
  }

  def saveNewResource(resourceRoot: ResourceRoot) = {
    val resourceRootDmo = resourceRootDmoFactory.fromResourceRoot(resourceRoot)
    resourceRootDmo.setResourceId(sequenceService.getNextValue(ResourceRootDmo.IdSequence))
    resourceRootDmo.setVersion(sequenceService.getNextValue(ResourceRootDmo.VersionSequence))
    val dmo = resourceRootDmoRepository.save(resourceRootDmo)
    log.debug(s"Saved resource ${dmo.getResourceType}/${dmo.getResourceId}")
    resourceRootFactory.fromResourceRootDmo(dmo)
  }

  def markResourcesDirty(atomGroup: AtomGroup, atomChangeEvents: Iterable[AtomChangeEvent]) = {
    val atomIds = atomChangeEvents.map(_.getAtomId)
    val resourceElementDmos = resourceElementDmoRepository.findByAtomIdInAndAtomTypeAndAtomOrigin(atomIds.asJava, atomGroup.getAtomType, atomGroup.getAtomOrigin)
    resourceElementDmos.asScala
      .map(getResourceRootDmo)
      .filter(_ != null)
      .map(markResourceDirty)
  }

  private[this] def getResourceRootDmo(resourceElementDmo: ResourceElementDmo) = {
    resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceElementDmo.getResourceType, resourceElementDmo.getResourceId)
  }

  private[this] def markResourceDirty(resourceRootDmo: ResourceRootDmo) = {
    log.debug(s"Mark ${resourceRootDmo.getResourceType}/${resourceRootDmo.getResourceId} as dirty")
    val version = sequenceService.getNextValue(ResourceRootDmo.VersionSequence)
    resourceRootDmo.setVersion(version)
    resourceRootFactory.fromResourceRootDmo(resourceRootDmoRepository.save(resourceRootDmo))
  }
}
