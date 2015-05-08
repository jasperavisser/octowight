package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.data.{Resource, ResourceRoot}
import org.springframework.stereotype.Component

@Component
class ResourceModelIdFactory {

  def resourceModelId(resource: Resource[_]) = {
    val resourceModelId = new ResourceModelId
    resourceModelId.setResourceId(resource.getId)
    resourceModelId.setResourceType(resource.getType)
    resourceModelId
  }

  def resourceModelId(resourceRoot: ResourceRoot) = {
    val resourceModelId = new ResourceModelId
    resourceModelId.setResourceId(resourceRoot.getResourceId)
    resourceModelId.setResourceType(resourceRoot.getResourceType)
    resourceModelId
  }
}
