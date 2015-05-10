package nl.haploid.octowight.registry

import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.registry.repository.{ResourceElementDmo, ResourceModelDmo, ResourceModelId, ResourceRootDmo}
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}

object TestData {

  def nextLong: Long = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType("olson")
    resourceRoot.setAtomId(nextLong)
    resourceRoot.setAtomOrigin("madison avenue")
    resourceRoot.setAtomType("draper")
    resourceRoot
  }

  def atomChangeEvent(atomGroup: AtomGroup) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin(atomGroup.getAtomOrigin)
    event.setAtomType(atomGroup.getAtomType)
    event
  }

  def atomChangeEvent(atomType: String) = {
    val event = new AtomChangeEvent
    event.setId(nextLong)
    event.setAtomId(nextLong)
    event.setAtomOrigin("everywhere")
    event.setAtomType(atomType)
    event
  }

  def resourceRootDmo: ResourceRootDmo = resourceRootDmo("olson")

  def resourceRootDmo(resourceType: String) = {
    val dmo = new ResourceRootDmo
    dmo.setResourceId(nextLong)
    dmo.setResourceType(resourceType)
    dmo.setAtomId(nextLong)
    dmo.setAtomOrigin("madison avenue")
    dmo.setAtomType("draper")
    dmo.setVersion(nextLong)
    dmo
  }

  def resourceElementDmo(resourceRootDmo: ResourceRootDmo, atomChangeEvent: AtomChangeEvent) = {
    val dmo = new ResourceElementDmo
    dmo.setResourceId(resourceRootDmo.getResourceId)
    dmo.setResourceType(resourceRootDmo.getResourceType)
    dmo.setAtomId(atomChangeEvent.getAtomId)
    dmo.setAtomOrigin(atomChangeEvent.getAtomOrigin)
    dmo.setAtomType(atomChangeEvent.getAtomType)
    dmo
  }

  def resourceElementDmo = {
    val dmo = new ResourceElementDmo
    dmo.setResourceId(nextLong)
    dmo.setResourceType("creative director")
    dmo.setAtomId(nextLong)
    dmo.setAtomOrigin("madison avenue")
    dmo.setAtomType("draper")
    dmo
  }

  def resourceModelDmo = {
    val expectedResourceModelDmo = new ResourceModelDmo
    val resourceModelId = new ResourceModelId
    resourceModelId.setResourceId(TestData.nextLong)
    resourceModelId.setResourceType(TestData.nextString)
    expectedResourceModelDmo.setId(resourceModelId)
    expectedResourceModelDmo.setBody(TestData.nextString)
    expectedResourceModelDmo
  }

  def atomGroup = {
    val atomGroup = new AtomGroup
    atomGroup.setAtomOrigin("new york")
    atomGroup.setAtomType("advertising agency")
    atomGroup
  }

  def resourceModelId = {
    val resourceModelId = new ResourceModelId
    resourceModelId.setResourceId(nextLong)
    resourceModelId.setResourceType(nextString)
    resourceModelId
  }
}
