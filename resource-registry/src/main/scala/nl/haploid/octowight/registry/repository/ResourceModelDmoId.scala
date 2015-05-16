package nl.haploid.octowight.registry.repository

import java.io.Serializable
import java.lang

import nl.haploid.octowight.registry.data.ResourceIdentifier

object ResourceModelDmoId {

  def apply(resource: ResourceIdentifier) =
    new ResourceModelDmoId(resourceId = resource.getId, resourceType = resource.getType)
}

case class ResourceModelDmoId(resourceId: lang.Long, resourceType: String) extends Serializable
