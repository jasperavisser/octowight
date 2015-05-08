package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.data.Resource
import nl.haploid.octowight.registry.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}

class ResourceModelDmoFactoryTest extends AbstractTest {
  @Tested private val resourceModelDmoFactory = new ResourceModelDmoFactory
  @Mocked private val resourceModelIdFactory: ResourceModelIdFactory = null

  "Resource model DMO factory" should "build DMO from resource" in {
    val resource = mock[Resource[_]]
    val resourceModelId = TestData.resourceModelId
    val body = TestData.nextString
    val version = TestData.nextLong
    expecting {
      resourceModelIdFactory.resourceModelId(resource) andReturn resourceModelId once()
      resource.getVersion andReturn version once()
    }
    whenExecuting(resourceModelIdFactory, resource) {
      val actualResourceModelDmo = resourceModelDmoFactory.fromResourceAndBody(resource, body)
      actualResourceModelDmo.getId should be(resourceModelId)
      actualResourceModelDmo.getBody should be(body)
      actualResourceModelDmo.getVersion should be(version)
    }
  }
}
