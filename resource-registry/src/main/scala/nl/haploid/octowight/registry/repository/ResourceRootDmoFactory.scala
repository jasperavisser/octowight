package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.data.ResourceRoot
import org.springframework.stereotype.Component

@Component
class ResourceRootDmoFactory {

  def fromResourceRoot(resourceRoot: ResourceRoot) = {
    val resourceRootDmo = new ResourceRootDmo
    resourceRootDmo.setAtomId(resourceRoot.getAtomId)
    resourceRootDmo.setAtomOrigin(resourceRoot.getAtomOrigin)
    resourceRootDmo.setAtomType(resourceRoot.getAtomType)
    resourceRootDmo.setResourceId(resourceRoot.getResourceId)
    resourceRootDmo.setResourceType(resourceRoot.getResourceType)
    resourceRootDmo
  }
}
