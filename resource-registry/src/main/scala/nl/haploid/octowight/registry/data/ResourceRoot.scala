package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo

object ResourceRoot {

  def apply(event: AtomChangeEvent, resourceType: String) = {
    val atom: Atom = new Atom(event.atomId, event.atomOrigin, event.atomCategory)
    new ResourceRoot(
      resourceId = null,
      resourceType = resourceType,
      root = atom,
      version = null)
  }

  def apply(resourceRootDmo: ResourceRootDmo) = {
    val atom = new Atom(resourceRootDmo.root.id, resourceRootDmo.root.origin, resourceRootDmo.root.category)
    new ResourceRoot(
      root = atom,
      resourceId = resourceRootDmo.resourceId,
      resourceType = resourceRootDmo.resourceType,
      tombstone = resourceRootDmo.tombstone,
      version = resourceRootDmo.version)
  }
}

// TODO: consider making most data objects immutable
case class ResourceRoot
(
  resourceId: lang.Long,
  resourceType: String,
  root: Atom,
  tombstone: Boolean = false,
  version: lang.Long
  ) extends ResourceIdentifier with Atomizable {

  override def getId: lang.Long = resourceId

  override def getType: String = resourceType

  def key = s"${root.origin}:${root.category}/${root.id}->$resourceType"

  override def toAtom: Atom = new Atom(root.id, root.origin, root.category)
}
