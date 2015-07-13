package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, ResourceIdentifier}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import scala.annotation.meta.field

object ResourceElementDmo {

  def apply(resourceIdentifier: ResourceIdentifier, atom: Atom) =
    new ResourceElementDmo(resourceCollection = resourceIdentifier.collection, resourceId = resourceIdentifier.id, atom = AtomDmo(atom))
}

@Document(collection = "resourceElement")
case class ResourceElementDmo
(
  @(Id@field) id: String = null,
  resourceCollection: String,
  resourceId: lang.Long,
  atom: AtomDmo)
