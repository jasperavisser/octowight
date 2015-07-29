package nl.haploid.octowight.registry.service

import nl.haploid.octowight.registry.data.{ResourceIdentifier, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceRegistryService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val sequenceService: SequenceService = null

  def saveResource(resourceRoot: ResourceRoot): Option[ResourceRoot] = {
    Option(findResourceRootDmo(resourceRoot)) match {
      case Some(resourceRootDmo) => if (resourceRootDmo.tombstone) Some(untombstoneResource(resourceRootDmo)) else None
      case None => Some(saveNewResource(resourceRoot))
    }
  }

  def markResourcesDirty(atomGroup: AtomGroup, atomChangeEvents: Iterable[AtomChangeEvent]) = {
    val atomIds = atomChangeEvents.map(event => Long.box(event.id))
    val resourceElementDmos = resourceElementDmoRepository.findByAtomIdInAndAtomCategoryAndAtomOrigin(
      atomIds.asJava, atomGroup.category, atomGroup.origin)
    resourceElementDmos.asScala
      .flatMap(element => findResourceRootDmo(element))
      .map(markResourceDirty)
  }

  def findResourceRoots(resourceIdentifiersByCollection: Map[String, Iterable[ResourceIdentifier]]): Map[String, Iterable[ResourceRoot]] = {
    val resourceRoots: Iterable[ResourceRoot] = resourceIdentifiersByCollection flatMap {
      case (collection, identifiers) =>
        val ids = identifiers.map(identifier => Long.box(identifier.id))
        resourceRootDmoRepository.findByResourceCollectionAndResourceIdIn(collection, ids.toList.asJava).asScala
          .map(ResourceRoot(_))
    }
    resourceRoots.groupBy(_.resourceCollection)
  }

  private[this] def findResourceRootDmo(resourceRoot: ResourceRoot): ResourceRootDmo = {
    resourceRootDmoRepository.findByResourceCollectionAndRootIdAndRootCategoryAndRootOrigin(
      resourceRoot.resourceCollection, resourceRoot.root.id, resourceRoot.root.category, resourceRoot.root.origin)
  }

  private[this] def findResourceRootDmo(resourceElementDmo: ResourceElementDmo) = {
    Option(resourceRootDmoRepository.findByResourceCollectionAndResourceId(
      resourceElementDmo.resourceCollection, resourceElementDmo.resourceId))
  }

  private[this] def markResourceDirty(resourceRootDmo: ResourceRootDmo) = {
    log.info(s"Mark ${resourceRootDmo.resourceCollection}/${resourceRootDmo.resourceId} as dirty")
    val version = sequenceService.nextValue(ResourceRootDmo.VersionSequence)
    val resourceRootDmoToSave = resourceRootDmo.copy(version = version)
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }

  private[this] def saveNewResource(resourceRoot: ResourceRoot): ResourceRoot = {
    val resourceId: Long = sequenceService.nextValue(ResourceRootDmo.IdSequence)
    val version: Long = sequenceService.nextValue(ResourceRootDmo.VersionSequence)
    val resourceRootDmoToSave = ResourceRootDmo(resourceRoot).copy(resourceId = resourceId, version = version)
    log.info(s"Save resource ${resourceRootDmoToSave.resourceCollection}/${resourceRootDmoToSave.resourceId}")
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }

  private[this] def untombstoneResource(resourceRootDmo: ResourceRootDmo): ResourceRoot = {
    log.info(s"Remove tombstone for ${resourceRootDmo.resourceCollection}/${resourceRootDmo.resourceId}")
    val resourceRootDmoToSave = resourceRootDmo.copy(tombstone = false)
    ResourceRoot(resourceRootDmoRepository.save(resourceRootDmoToSave))
  }
}
