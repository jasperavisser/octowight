package nl.haploid.octowight.registry.data

import java.lang

import nl.haploid.octowight.registry.repository.ResourceRootDmo

object ResourceRoot {

  def apply(root: Atom, resourceCollection: String) =
    new ResourceRoot(resourceId = null, resourceCollection = resourceCollection, root = root, version = null)

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
  resourceCollection: String,
  root: Atom,
  tombstone: Boolean = false,
  version: lang.Long
  ) extends Atomizable {

  def key = s"${root.origin}:${root.category}/${root.id}->$resourceCollection"

  override def toAtom: Atom = new Atom(root.id, root.origin, root.category)
}
