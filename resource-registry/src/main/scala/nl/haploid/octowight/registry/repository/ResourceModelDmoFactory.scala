package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.data.Resource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ResourceModelDmoFactory {
  @Autowired private val resourceModelIdFactory: ResourceModelIdFactory = null

  def fromResourceAndBody(resource: Resource[_], body: String) = {
    val resourceModelDmo = new ResourceModelDmo
    val resourceModelId = resourceModelIdFactory.resourceModelId(resource)
    resourceModelDmo.setId(resourceModelId)
    resourceModelDmo.setBody(body)
    resourceModelDmo.setVersion(resource.getVersion)
    resourceModelDmo
  }
}
