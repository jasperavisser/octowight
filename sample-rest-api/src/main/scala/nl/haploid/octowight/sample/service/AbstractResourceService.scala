package nl.haploid.octowight.sample.service

import java.lang

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.data.{CaptainResource, ResourceNotFoundException}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import scala.collection.JavaConverters._

// TODO: test
abstract class AbstractResourceService[M <: Model, R <: Resource[M]] {
  val log = LoggerFactory.getLogger(getClass)

  @Autowired private val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private val resourceRootFactory: ResourceRootFactory = null
  @Autowired private val resourceElementDmoFactory: ResourceElementDmoFactory = null
  @Autowired private val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Autowired private val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Autowired private val resourceModelIdFactory: ResourceModelIdFactory = null
  @Autowired private val modelSerializer: ModelSerializer[M] = null
  @Autowired private val resourceFactory: ResourceFactory[R] = null

  def getResourceType: String

  def getModel(resourceId: lang.Long) = {
    log.debug(s"Get model for resource $getResourceType/$resourceId")
    val resourceRoot = getResourceRoot(getResourceType, resourceId)
    val cachedModel = getCachedModel(resourceRoot)
    if (cachedModel != null) {
      log.debug(s"Using cached model for resource $getResourceType/$resourceId")
      cachedModel
    } else {
      val resource = resourceFactory.fromResourceRoot(resourceRoot)
      saveResourceElements(resource)
      val model = resource.getModel
      saveModel(resource, model)
      model
    }
  }

  private def getModelOption(resourceId: Long) = {
    try {
      Some(getModel(resourceId))
    } catch {
      case e: ResourceNotFoundException => None
    }
  }

  def getAllModels = {
    resourceRootDmoRepository.findByResourceType(CaptainResource.ResourceType).asScala
      .map(_.getResourceId)
      .flatMap(getModelOption(_))
      .asJava
  }

  def getCachedModel(resourceRoot: ResourceRoot) = {
    val resourceModelId = resourceModelIdFactory.resourceModelId(resourceRoot)
    val resourceModelDmo = resourceModelDmoRepository.findOne(resourceModelId)
    if (resourceModelDmo != null && (resourceModelDmo.getVersion == resourceRoot.getVersion)) {
      modelSerializer.deserialize(resourceModelDmo.getBody, getModelClass)
    } else {
      null
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

  private def createModelDmo(resource: R, model: M) = {
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

  def saveModel(resource: R, model: M) {
    log.debug(s"Save model for resource ${resource.getType}/${resource.getId}")
    val resourceModelDmo = createModelDmo(resource, model)
    resourceModelDmoRepository.save(resourceModelDmo)
  }

  def saveResourceElements(resource: R) {
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType, resource.getId)
    resource.getAtoms.asScala
      .foreach(atom => resourceElementDmoRepository.save(resourceElementDmoFactory.fromResourceAndAtom(resource, atom)))
  }
}
