package nl.haploid.octowight.sample.service

import nl.haploid.octowight.Mocked
import nl.haploid.octowight.registry.data.{ModelSerializer, ResourceFactory, ResourceRootFactory}
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.data.{CaptainModel, CaptainResource}
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import org.easymock.EasyMock
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

class CaptainServiceTest extends AbstractTest {
  @Mocked private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Mocked private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Mocked private[this] val resourceRootFactory: ResourceRootFactory = null
  @Mocked private[this] val resourceElementDmoFactory: ResourceElementDmoFactory = null
  @Mocked private[this] val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Mocked private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Mocked private[this] val resourceModelIdFactory: ResourceModelIdFactory = null
  @Mocked private[this] val modelSerializer: ModelSerializer[CaptainModel] = null
  @Mocked private[this] val resourceFactory: ResourceFactory[CaptainResource] = null

  "Captain service" should "get model from origin" in {
    val captainService = withMocks(EasyMock.createMockBuilder(classOf[CaptainService])
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
      .addMockedMethod("getCachedModel")
      .addMockedMethod("saveResourceElements")
      .addMockedMethod("saveModel")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(captainService, "log", log)

    val captainResource = mock[CaptainResource]
    val resourceId = TestData.nextLong
    val resourceType = CaptainResource.ResourceType
    val resourceRoot = TestData.resourceRoot(resourceId)
    val expectedCaptainModel = TestData.captainModel
    expecting {
      captainService.getResourceType andReturn resourceType times 2
      captainService.getResourceRoot(resourceType, resourceId) andReturn resourceRoot once()
      captainService.getCachedModel(resourceRoot) andReturn None once()
      resourceFactory.fromResourceRoot(resourceRoot) andReturn captainResource once()
      captainService.saveResourceElements(captainResource) once()
      captainResource.getModel andReturn expectedCaptainModel once()
      captainService.saveModel(captainResource, expectedCaptainModel) once()
    }
    whenExecuting(captainService, resourceFactory, captainResource) {
      val actualCaptainModel = captainService.getModel(resourceId)
      actualCaptainModel should be(expectedCaptainModel)
    }
  }

  "Captain service" should "get model from cache" in {
    val captainService = withMocks(EasyMock.createMockBuilder(classOf[CaptainService])
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
      .addMockedMethod("getCachedModel")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(captainService, "log", log)

    val resourceId = TestData.nextLong
    val resourceType = CaptainResource.ResourceType
    val resourceRoot = TestData.resourceRoot(resourceId)
    val expectedCaptainModel = TestData.captainModel
    expecting {
      captainService.getResourceType andReturn resourceType times 2
      captainService.getResourceRoot(resourceType, resourceId) andReturn resourceRoot once()
      captainService.getCachedModel(resourceRoot) andReturn Some(expectedCaptainModel) once()
    }
    whenExecuting(captainService) {
      val actualCaptainModel = captainService.getModel(resourceId)
      actualCaptainModel should be(expectedCaptainModel)
    }
  }
}
