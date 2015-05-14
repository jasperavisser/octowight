package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.data.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConverters._
import scala.util.{Success, Try}

abstract class AbstractResourceService[M <: Model, R <: Resource[M]] {
  val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val modelCacheService: ModelCacheService[M, R] = null
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val resourceFactory: ResourceFactory[R] = null

  def getModelClass: Class[M]

  def getResourceType: String

  def getModel(resourceId: Long): M = {
    log.debug(s"Get model for resource $getResourceType/$resourceId")
    val resourceRoot = getResourceRoot(getResourceType, resourceId)
    modelCacheService.get(resourceRoot, getModelClass).getOrElse({
      val resource = resourceFactory.fromResourceRoot(resourceRoot)
      saveResourceElements(resource)
      val model = resource.getModel
      modelCacheService.put(resource, model)
      model
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
    resourceRootDmoRepository.findByResourceType(getResourceType).asScala
      .map(_.getResourceId)
      .flatMap(getModelOption(_))
  }

  def getResourceRoot(resourceType: String, resourceId: Long) = {
    Option(resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId)) match {
      case Some(dmo) => ResourceRoot(dmo)
      case None => throw new ResourceNotFoundException
    }
  }

  // TODO: test
  def saveResourceElements(resource: R) = {
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType, resource.getId)
    resource.getAtoms
      .foreach(atom => resourceElementDmoRepository.save(ResourceElementDmo(resource, atom)))
  }
}
