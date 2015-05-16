package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.AtomChangeEvent
import nl.haploid.octowight.registry.repository.ResourceRootDmo

object ResourceRoot {

  def apply(event: AtomChangeEvent, resourceCollection: String) = {
    val atom: Atom = new Atom(event.atomId, event.atomOrigin, event.atomCategory)
    new ResourceRoot(
      resourceId = null,
      resourceCollection = resourceCollection,
      root = atom,
      version = null)
  }

  def apply(resourceRootDmo: ResourceRootDmo) = {
    val atom = new Atom(resourceRootDmo.root.id, resourceRootDmo.root.origin, resourceRootDmo.root.category)
    new ResourceRoot(
      root = atom,
      resourceId = resourceRootDmo.resourceId,
      resourceCollection = resourceRootDmo.resourceCollection,
      tombstone = resourceRootDmo.tombstone,
      version = resourceRootDmo.version)
  }
}

case class ResourceRoot
(
  resourceId: lang.Long,
  resourceCollection: String, // TODO: collection
  root: Atom,
  tombstone: Boolean = false,
  version: lang.Long
  ) extends Atomizable {

  def key = s"${root.origin}:${root.category}/${root.id}->$resourceCollection"

  override def toAtom: Atom = new Atom(root.id, root.origin, root.category)
}
