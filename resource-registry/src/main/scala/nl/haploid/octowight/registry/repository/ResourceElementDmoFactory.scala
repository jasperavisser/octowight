package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.data.{Atom, Resource}
import org.springframework.stereotype.Component

@Component
class ResourceElementDmoFactory {

  def fromResourceAndAtom(resource: Resource[_], atom: Atom) = {
    val dmo = new ResourceElementDmo
    dmo.setAtomId(atom.getAtomId)
    dmo.setAtomOrigin(atom.getAtomOrigin)
    dmo.setAtomType(atom.getAtomType)
    dmo.setResourceId(resource.getId)
    dmo.setResourceType(resource.getType)
    dmo
  }
}
