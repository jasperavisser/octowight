package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.Tested
import nl.haploid.octowight.registry.{AbstractTest, TestData}

class ResourceRootDmoFactoryTest extends AbstractTest {
  @Tested private val resourceRootDmoFactory = new ResourceRootDmoFactory

  "Resource root DMO factory" should "build DMO from resource root" in {
    val resourceRoot = TestData.resourceRoot(123l)
    val resourceRootDmo = resourceRootDmoFactory.fromResourceRoot(resourceRoot)
    resourceRootDmo.getAtomId should be(resourceRoot.getAtomId)
    resourceRootDmo.getAtomType should be(resourceRoot.getAtomType)
    resourceRootDmo.getAtomOrigin should be(resourceRoot.getAtomOrigin)
    resourceRootDmo.getResourceId should be(resourceRoot.getResourceId)
    resourceRootDmo.getResourceType should be(resourceRoot.getResourceType)
  }
}
