package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.Resource
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

object ResourceModelDmo {

  def apply(resource: Resource[_], body: String) = {
    new ResourceModelDmo(id = new ResourceModelDmoId(resource.id, resource.collection), body = body, version = resource.version)
  }
}

@Document(collection = "resourceModel")
case class ResourceModelDmo
(
  @(Id@field) id: ResourceModelDmoId,
  version: lang.Long,
  body: String)
