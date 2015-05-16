package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractTest, TestData}

class ResourceRootDmoFactoryTest extends AbstractTest {

  "Resource root DMO factory" should "build DMO from resource root" in {
    val resourceRoot = TestData.resourceRoot(123l)
    val resourceRootDmo = ResourceRootDmo(resourceRoot)
    resourceRootDmo.getRoot.getId should be(resourceRoot.getAtomId)
    resourceRootDmo.getRoot.getCategory should be(resourceRoot.getAtomCategory)
    resourceRootDmo.getRoot.getOrigin should be(resourceRoot.getAtomOrigin)
    resourceRootDmo.getResourceId should be(resourceRoot.getResourceId)
    resourceRootDmo.getResourceType should be(resourceRoot.getResourceType)
  }
}
