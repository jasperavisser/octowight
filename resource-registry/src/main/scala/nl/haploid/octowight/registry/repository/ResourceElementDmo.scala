package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, Resource}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

object ResourceElementDmo {

  def apply(resource: Resource[_], atom: Atom) = {
    new ResourceElementDmo(resourceId = resource.getId, resourceType = resource.getType, atom = AtomDmo(atom))
  }
}

@Document(collection = "resourceElement")
case class ResourceElementDmo
(
  @(Id@field) id: String = null,
  resourceId: lang.Long,
  resourceType: String,
  atom: AtomDmo)
