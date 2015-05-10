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

  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val resourceRootFactory: ResourceRootFactory = null
  @Autowired private[this] val resourceElementDmoFactory: ResourceElementDmoFactory = null
  @Autowired private[this] val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Autowired private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Autowired private[this] val resourceModelIdFactory: ResourceModelIdFactory = null
  @Autowired private[this] val modelSerializer: ModelSerializer[M] = null
  @Autowired protected[this] val resourceFactory: ResourceFactory[R] = null

  def getResourceType: String

  def getModel(resourceId: Long): M = {
    log.debug(s"Get model for resource $getResourceType/$resourceId")
    val resourceRoot = getResourceRoot(getResourceType, resourceId)
    getCachedModel(resourceRoot).getOrElse({
      val resource = resourceFactory.fromResourceRoot(resourceRoot)
      saveResourceElements(resource)
      val model = resource.getModel
      saveModel(resource, model)
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

  // TODO: test
  def getCachedModel(resourceRoot: ResourceRoot): Option[M] = {
    val resourceModelId = resourceModelIdFactory.resourceModelId(resourceRoot)
    val resourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId)
    if (resourceModelDmo != null && (resourceModelDmo.getVersion == resourceRoot.getVersion)) {
      log.debug(s"Using cached model for resource $getResourceType/${resourceRoot.getResourceId}")
      Some(modelSerializer.deserialize(resourceModelDmo.getBody, getModelClass))
    } else {
      None
    }
  }

  def getModelClass: Class[M]

  def getResourceRoot(resourceType: String, resourceId: Long) = {
    val resourceRootDmo = resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId)
    if (resourceRootDmo == null) {
      throw new ResourceNotFoundException
    }
    resourceRootFactory.fromResourceRootDmo(resourceRootDmo)
  }

  private[this] def createModelDmo(resource: R, model: M): ResourceModelDmo = {
    val body = modelSerializer.serialize(model)
    val resourceModelId = resourceModelIdFactory.resourceModelId(resource)
    val resourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId)
    if (resourceModelDmo != null) {
      resourceModelDmo.setBody(body)
      resourceModelDmo.setVersion(resource.getVersion)
      resourceModelDmo
    } else {
      resourceModelDmoFactory.fromResourceAndBody(resource, body)
    }
  }

  // TODO: test
  def saveModel(resource: R, model: M): Unit = {
    log.debug(s"Save model for resource ${resource.getType}/${resource.getId}")
    val resourceModelDmo = createModelDmo(resource, model)
    resourceModelDmoRepository.save(resourceModelDmo)
  }

  // TODO: test
  def saveResourceElements(resource: R) = {
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType, resource.getId)
    resource.getAtoms
      .foreach(atom => resourceElementDmoRepository.save(resourceElementDmoFactory.fromResourceAndAtom(resource, atom)))
  }
}
