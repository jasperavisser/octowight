package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.data.ResourceFactory
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}
import org.junit.Test

class AbstractResourceServiceTest extends AbstractTest {

  private abstract class MockModel extends Model

  private abstract class MockResource extends Resource[AbstractResourceServiceTest#MockModel]

  @Tested private val service: AbstractResourceService[AbstractResourceServiceTest#MockModel, AbstractResourceServiceTest#MockResource] = null
  @Mocked private val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Mocked private val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Mocked private val resourceRootFactory: ResourceRootFactory = null
  @Mocked private val resourceElementDmoFactory: ResourceElementDmoFactory = null
  @Mocked private val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Mocked private val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Mocked private val resourceModelIdFactory: ResourceModelIdFactory = null
  @Mocked private var modelSerializer: ModelSerializer[AbstractResourceServiceTest#MockModel] = null
  @Mocked private var resourceFactory: ResourceFactory[AbstractResourceServiceTest#MockResource] = null

  "Abstract resource service" should "get resource root" in {
    val resourceId = TestData.nextLong
    val resourceType = TestData.nextString
    val resourceRootDmo = TestData.resourceRootDmo
    val expectedResourceRoot = TestData.resourceRoot
    expecting {
      resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId) andReturn resourceRootDmo once()
      resourceRootFactory.fromResourceRootDmo(resourceRootDmo) andReturn expectedResourceRoot once()
    }
    whenExecuting(resourceRootDmoRepository, resourceRootFactory) {
      val actualResourceRoot = service.getResourceRoot(resourceType, resourceId)
      actualResourceRoot should be(expectedResourceRoot)
    }
  }

  @Test def testSaveModel {
  }

  @Test def testSaveResourceElements {
  }

  @Test def testSaveResourceElement {
  }
}
