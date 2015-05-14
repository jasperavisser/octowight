package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.{Model, ModelSerializer, Resource, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ModelCacheService[M <: Model, R <: Resource[M]] {
  val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val modelSerializer: ModelSerializer[M] = null
  @Autowired private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null

  def get(resourceRoot: ResourceRoot, modelClass: Class[M]): Option[M] = {
    val id = ResourceModelId(resourceRoot)
    Option(resourceModelDmoRepository.findByIdAndVersion(id, resourceRoot.getVersion)) match {
      case Some(dmo) =>
        log.debug(s"Using cached model for resource ${resourceRoot.getResourceType}/${resourceRoot.getResourceId} version ${resourceRoot.getVersion}")
        Some(modelSerializer.deserialize(dmo.getBody, modelClass))
      case None =>
        None
    }
  }

  def put(resource: R, model: M): Unit = {
    log.debug(s"Save model for resource ${resource.getType}/${resource.getId}")
    resourceModelDmoRepository.save(resourceModelDmo(resource, model))
  }

  private[this] def resourceModelDmo(resource: R, model: M): ResourceModelDmo = {
    val body = modelSerializer.serialize(model)
    Option(resourceModelDmoRepository.findOne(ResourceModelId(resource))) match {
      case Some(dmo) =>
        dmo.setBody(body)
        dmo.setVersion(resource.getVersion)
        dmo
      case None => ResourceModelDmo(resource, body)
    }
  }
}
