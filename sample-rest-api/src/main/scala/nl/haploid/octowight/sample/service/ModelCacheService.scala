package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.{Model, ModelSerializer, Resource, ResourceRoot}
import nl.haploid.octowight.registry.repository.{ResourceModelDmo, ResourceModelDmoFactory, ResourceModelDmoRepository, ResourceModelIdFactory}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ModelCacheService[M <: Model, R <: Resource[M]] {
  val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val modelSerializer: ModelSerializer[M] = null
  @Autowired private[this] val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Autowired private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Autowired private[this] val resourceModelIdFactory: ResourceModelIdFactory = null

  def get(resourceRoot: ResourceRoot, modelClass: Class[M]): Option[M] = {
    val id = resourceModelIdFactory.resourceModelId(resourceRoot)
    Option(resourceModelDmoRepository.findByIdAndVersion(id, resourceRoot.getVersion)) match {
      case Some(dmo) =>
        log.debug(s"Using cached model for resource ${resourceRoot.getResourceType}/${resourceRoot.getResourceId} version ${resourceRoot.getVersion}")
        Some(modelSerializer.deserialize(dmo.getBody, modelClass))
      case None =>
        None
    }
  }

  // TODO: test
  def put(resource: R, model: M): Unit = {
    log.debug(s"Save model for resource ${resource.getType}/${resource.getId}")
    resourceModelDmoRepository.save(resourceModelDmo(resource, model))
  }

  private[this] def resourceModelDmo(resource: R, model: M): ResourceModelDmo = {
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
}
