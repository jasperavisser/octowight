package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.{Model, ModelSerializer, Resource, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ModelCacheService[M <: Model, R <: Resource[M]] {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val modelSerializer: ModelSerializer[M] = null
  @Autowired private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null

  def get(resourceRoot: ResourceRoot, modelClass: Class[M]): Option[M] = {
    val id = new ResourceModelDmoId(resourceRoot.resourceId, resourceRoot.resourceCollection)
    Option(resourceModelDmoRepository.findByIdAndVersion(id, resourceRoot.version)) match {
      case Some(dmo) =>
        log.debug(s"Using cached model for resource ${resourceRoot.resourceCollection}/${resourceRoot.resourceId} version ${resourceRoot.version}")
        Some(modelSerializer.deserialize(dmo.body, modelClass))
      case None =>
        None
    }
  }

  def put(resource: R, model: M): Unit = {
    log.debug(s"Save model for resource ${resource.collection}/${resource.id}")
    resourceModelDmoRepository.save(resourceModelDmo(resource, model))
  }

  private[this] def resourceModelDmo(resource: R, model: M): ResourceModelDmo = {
    val body = modelSerializer.serialize(model)
    val id: ResourceModelDmoId = new ResourceModelDmoId(resource.id, resource.collection)
    Option(resourceModelDmoRepository.findOne(id)) match {
      case Some(dmo) => dmo.copy(body = body, version = resource.version)
      case None => ResourceModelDmo(resource, body)
    }
  }
}
