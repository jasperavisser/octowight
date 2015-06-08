package nl.haploid.octowight.sample

import java.lang

import nl.haploid.octowight.registry.data.ResourceIdentifier

// TODO: move to resource-registry project (or cache project ...)
trait Resource {
  def resourceIdentifier: ResourceIdentifier

  def tombstone: lang.Boolean
}

// TODO: it will be harder to deserialize not knowing which subclass to deserialize to
case class ExistingResource(resourceIdentifier: ResourceIdentifier, model: String) extends Resource {
  override val tombstone: lang.Boolean = false
}

case class MissingResource(resourceIdentifier: ResourceIdentifier) extends Resource {
  override val tombstone: lang.Boolean = true
}
