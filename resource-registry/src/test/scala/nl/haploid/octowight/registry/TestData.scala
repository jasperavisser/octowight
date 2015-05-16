package nl.haploid.octowight.registry

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, ResourceRoot}
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}

object TestData {

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin(nextString)
    resourceRoot.setAtomCategory(nextString)
    resourceRoot
  }

  def atomChangeEvent(atomGroup: AtomGroup) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(atomGroup.getAtomOrigin)
    event.setAtomCategory(atomGroup.getAtomCategory)
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
    val dmo = new ResourceRootDmo
    dmo.setResourceId(nextLong)
    dmo.setResourceType(resourceType)
    dmo.setRoot(AtomDmo(new Atom(nextLong, nextString, nextString)))
    dmo.setVersion(nextLong)
    dmo
  }

  // TODO: not random, so this could be an apply method
  def resourceElementDmo(resourceRootDmo: ResourceRootDmo, atomChangeEvent: AtomChangeEvent) = {
    val dmo = new ResourceElementDmo
    dmo.setResourceId(resourceRootDmo.getResourceId)
    dmo.setResourceType(resourceRootDmo.getResourceType)
    dmo.setAtom(AtomDmo(new Atom(atomChangeEvent.getAtomId, atomChangeEvent.getAtomOrigin, atomChangeEvent.getAtomCategory)))
    dmo
  }

  def resourceElementDmo = {
    val dmo = new ResourceElementDmo
    dmo.setResourceId(nextLong)
    dmo.setResourceType(nextString)
    dmo.setAtom(AtomDmo(new Atom(nextLong, nextString, nextString)))
    dmo
  }

  def resourceModelDmo = {
    val expectedResourceModelDmo = new ResourceModelDmo
    val resourceModelId = new ResourceModelDmoId
    resourceModelId.setResourceId(TestData.nextLong)
    resourceModelId.setResourceType(TestData.nextString)
    expectedResourceModelDmo.setId(resourceModelId)
    expectedResourceModelDmo.setBody(TestData.nextString)
    expectedResourceModelDmo
  }

  def resourceModelId = {
    val resourceModelId = new ResourceModelDmoId
    resourceModelId.setResourceId(nextLong)
    resourceModelId.setResourceType(nextString)
    resourceModelId
  }

  def atomGroup = {
    val atomGroup = new AtomGroup
    atomGroup.setAtomOrigin(TestData.nextString)
    atomGroup.setAtomCategory(TestData.nextString)
    atomGroup
  }
}
