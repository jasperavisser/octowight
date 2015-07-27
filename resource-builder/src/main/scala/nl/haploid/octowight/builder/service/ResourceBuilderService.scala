package nl.haploid.octowight.builder.service

import nl.haploid.octowight.registry.data.{ResourceMessage, ResourceRoot}
import nl.haploid.octowight.registry.service.ResourceElementRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ResourceBuilderService {
  @Autowired private[this] val resourceBuilders: ResourceBuilders = null
  @Autowired private[this] val resourceElementRegistryService: ResourceElementRegistryService = null

  def buildResources(resourceRootsByCollection: Map[String, Iterable[ResourceRoot]]): Iterable[ResourceMessage] = {
    resourceRootsByCollection flatMap {
      case (collection, resourceRoots) => buildResources(collection, resourceRoots)
    }
  }

  private[this] def buildResources(collection: String, resourceRoots: Iterable[ResourceRoot]): Iterable[ResourceMessage] = {
    val resourcesAndAtoms = resourceBuilders.forCollection(collection).flatMap(_.build(resourceRoots))
    resourcesAndAtoms map {
      case (resource, atoms) =>
        resourceElementRegistryService.saveResourceElements(resource.resourceIdentifier, atoms)
        resource
    }
  }
}
