package nl.haploid.octowight.registry

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceIdentifier, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}

object TestData {

  def atom = new Atom(nextLong, nextString, nextString)

  def atomChangeEvent(atomCategory: String) =
    AtomChangeEvent(id = nextLong, origin = nextString, category = atomCategory)

  def atomChangeEvent(atomGroup: AtomGroup) =
    AtomChangeEvent(id = nextLong, origin = atomGroup.origin, category = atomGroup.category)

  def atomGroup = new AtomGroup(origin = TestData.nextString, category = TestData.nextString)

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  // TODO: not random, so this could be an apply method
  def resourceElementDmo(resourceRootDmo: ResourceRootDmo, atomChangeEvent: AtomChangeEvent) = {
    val atomDmo: AtomDmo = AtomDmo(new Atom(atomChangeEvent.id, atomChangeEvent.origin, atomChangeEvent.category))
    new ResourceElementDmo(resourceId = resourceRootDmo.resourceId, resourceCollection = resourceRootDmo.resourceCollection, atom = atomDmo)
  }

  def resourceElementDmo =
    new ResourceElementDmo(resourceId = nextLong, resourceCollection = nextString, atom = AtomDmo(atom))

  def resourceIdentifier = new ResourceIdentifier(collection = nextString, id = nextLong)

  def resourceModelDmo = new ResourceModelDmo(id = resourceModelDmoId, body = nextString, version = nextLong)

  def resourceModelDmoId = new ResourceModelDmoId(resourceId = nextLong, resourceCollection = nextString)

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)

  def resourceRootDmo: ResourceRootDmo = resourceRootDmo(nextString)

  def resourceRootDmo(resourceCollection: String) =
    new ResourceRootDmo(
      resourceId = nextLong,
      resourceCollection = resourceCollection,
      root = AtomDmo(atom),
      version = nextLong)
}
