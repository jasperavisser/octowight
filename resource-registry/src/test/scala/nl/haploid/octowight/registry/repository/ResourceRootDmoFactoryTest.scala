package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractTest, TestData}

class ResourceRootDmoFactoryTest extends AbstractTest {

  "Resource root DMO factory" should "build DMO from resource root" in {
    val resourceRoot = TestData.resourceRoot(123l)
    val resourceRootDmo = ResourceRootDmo(resourceRoot)
    resourceRootDmo.getRoot.getId should be(resourceRoot.getRoot.id)
    resourceRootDmo.getRoot.getCategory should be(resourceRoot.getRoot.category)
    resourceRootDmo.getRoot.getOrigin should be(resourceRoot.getRoot.origin)
    resourceRootDmo.getResourceId should be(resourceRoot.getResourceId)
    resourceRootDmo.getResourceType should be(resourceRoot.getResourceType)
  }
}
