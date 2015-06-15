package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.data.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

abstract class AbstractResourceService[M <: Model, R <: Resource[M]] {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val modelCacheService: ModelCacheService[M, R] = null
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val resourceFactory: ResourceFactory[R] = null

  def modelClass: Class[M]

  def collection: String

  def getModel(resourceId: Long): M = {
    log.debug(s"Get model for resource $collection/$resourceId")
    val resourceRoot = getResourceRoot(collection, resourceId)
    modelCacheService.get(resourceRoot, modelClass).getOrElse({
      resourceFactory.fromResourceRoot(resourceRoot) match {
        case Some(resource) =>
          saveResourceElements(resource)
          val model = resource.model
          modelCacheService.put(resource, model)
          model
        case None =>
          tombstoneResource(resourceRoot)
          throw new ResourceNotFoundException
      }
    })
  }

  def getModelOption(resourceId: Long): Option[M] = {
    val result = Try(getModel(resourceId))
    result match {
      case Success(model) => Some(model)
      case _ => None
    }
  }

  def getAllModels: Iterable[M] = {
    resourceRootDmoRepository.findByResourceCollectionAndTombstone(collection, tombstone = false).asScala
      .map(_.resourceId)
      .flatMap(getModelOption(_))
  }

  def getResourceRoot(resourceCollection: String, resourceId: Long) = {
    Option(resourceRootDmoRepository.findByResourceCollectionAndResourceId(resourceCollection, resourceId)) match {
      case Some(dmo) => ResourceRoot(dmo)
      case None => throw new ResourceNotFoundException
    }
  }

  def saveResourceElements(resource: R) = {
    resourceElementDmoRepository.deleteByResourceCollectionAndResourceId(resource.collection, resource.id)
    resource.atoms
      .foreach(atom => resourceElementDmoRepository.save(ResourceElementDmo(resource, atom)))
  }

  def tombstoneResource(resourceRoot: ResourceRoot): Unit = {
    log.debug(s"Tombstone resource ${resourceRoot.resourceCollection}/${resourceRoot.resourceId}")
    val resourceRootDmo = resourceRootDmoRepository.findByResourceCollectionAndResourceId(resourceRoot.resourceCollection, resourceRoot.resourceId)
    val resourceRootDmoToSave = resourceRootDmo.copy(tombstone = true)
    resourceRootDmoRepository.save(resourceRootDmoToSave)
  }
}
