package nl.haploid.octowight.registry.data

import nl.haploid.octowight.Tested
import nl.haploid.octowight.registry.{AbstractTest, TestData}

class ResourceRootFactoryTest extends AbstractTest {
  @Tested private val resourceRootFactory: ResourceRootFactory = null

  "Resource root factory" should "build from event & resource type" in {
    val event = TestData.atomChangeEvent("pam")
    val resourceType = "greer"
    val resourceRoot = resourceRootFactory.fromAtomChangeEvent(event, resourceType)
    resourceRoot.getAtomId should be(event.getAtomId)
    resourceRoot.getAtomOrigin should be(event.getAtomOrigin)
    resourceRoot.getAtomType should be(event.getAtomType)
    resourceRoot.getResourceId should be(null)
    resourceRoot.getResourceType should be(resourceType)
  }

  "Resource root factory" should "build from resource root DMO" in {
    val resourceRootDmo = TestData.resourceRootDmo
    val resourceRoot = resourceRootFactory.fromResourceRootDmo(resourceRootDmo)
    resourceRoot.getAtomId should be(resourceRootDmo.getAtomId)
    resourceRoot.getAtomType should be(resourceRootDmo.getAtomType)
    resourceRoot.getAtomOrigin should be(resourceRootDmo.getAtomOrigin)
    resourceRoot.getResourceId should be(resourceRootDmo.getResourceId)
    resourceRoot.getResourceType should be(resourceRootDmo.getResourceType)
  }
}
