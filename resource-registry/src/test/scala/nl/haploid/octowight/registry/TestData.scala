package nl.haploid.octowight.registry

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}

object TestData {

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceType = nextString, root = atom, version = null)

  def atomChangeEvent(atomGroup: AtomGroup) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(atomGroup.getOrigin)
    event.setAtomCategory(atomGroup.getCategory)
    event
  }

  def atomChangeEvent(atomCategory: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(nextString)
    event.setAtomCategory(atomCategory)
    event
  }

  def resourceRootDmo: ResourceRootDmo = resourceRootDmo(nextString)

  def resourceRootDmo(resourceType: String) = {
    new ResourceRootDmo(
      resourceId = nextLong,
      resourceType = resourceType,
      root = AtomDmo(atom),
      version = nextLong)
  }

  // TODO: not random, so this could be an apply method
  def resourceElementDmo(resourceRootDmo: ResourceRootDmo, atomChangeEvent: AtomChangeEvent) = {
    val atomDmo: AtomDmo = AtomDmo(new Atom(atomChangeEvent.getAtomId, atomChangeEvent.getAtomOrigin, atomChangeEvent.getAtomCategory))
    new ResourceElementDmo(resourceId = resourceRootDmo.resourceId, resourceType = resourceRootDmo.resourceType, atom = atomDmo)
  }

  def resourceElementDmo = {
    new ResourceElementDmo(resourceId = nextLong, resourceType = nextString, atom = AtomDmo(atom))
  }

  def atom = new Atom(nextLong, nextString, nextString)

  def resourceModelDmo = new ResourceModelDmo(id = resourceModelDmoId, body = nextString, version = nextLong)

  def resourceModelDmoId = new ResourceModelDmoId(resourceId = nextLong, resourceType = nextString)

  def atomGroup = {
    val atomGroup = new AtomGroup
    atomGroup.setOrigin(TestData.nextString)
    atomGroup.setCategory(TestData.nextString)
    atomGroup
  }
}
