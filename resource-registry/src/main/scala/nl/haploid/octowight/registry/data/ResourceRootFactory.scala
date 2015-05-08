package nl.haploid.octowight.registry.data

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo
import org.springframework.stereotype.Component

@Component
class ResourceRootFactory {

  def fromAtomChangeEvent(event: AtomChangeEvent, resourceType: String) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(event.getAtomId)
    resourceRoot.setAtomOrigin(event.getAtomOrigin)
    resourceRoot.setAtomType(event.getAtomType)
    resourceRoot.setResourceType(resourceType)
    resourceRoot
  }

  def fromResourceRootDmo(resourceRootDmo: ResourceRootDmo) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setAtomId(resourceRootDmo.getAtomId)
    resourceRoot.setAtomOrigin(resourceRootDmo.getAtomOrigin)
    resourceRoot.setAtomType(resourceRootDmo.getAtomType)
    resourceRoot.setResourceId(resourceRootDmo.getResourceId)
    resourceRoot.setResourceType(resourceRootDmo.getResourceType)
    resourceRoot.setVersion(resourceRootDmo.getVersion)
    resourceRoot
  }
}
