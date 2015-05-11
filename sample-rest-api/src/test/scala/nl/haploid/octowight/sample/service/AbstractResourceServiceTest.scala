package nl.haploid.octowight.sample.service

import java.util

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}
import org.easymock.EasyMock
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

class AbstractResourceServiceTest extends AbstractTest {

  abstract class MockModel extends Model

  abstract class MockResource extends Resource[MockModel]

  abstract class MockResourceService extends AbstractResourceService[MockModel, MockResource]

  @Tested private[this] val resourceService: MockResourceService = null
  @Mocked private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Mocked private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Mocked private[this] val resourceRootFactory: ResourceRootFactory = null
  @Mocked private[this] val resourceElementDmoFactory: ResourceElementDmoFactory = null
  @Mocked private[this] val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Mocked private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Mocked private[this] val resourceModelIdFactory: ResourceModelIdFactory = null
  @Mocked private[this] val modelSerializer: ModelSerializer[MockModel] = null
  @Mocked private[this] val resourceFactory: ResourceFactory[MockResource] = null

  "Abstract resource service" should "get model from origin" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
      .addMockedMethod("getCachedModel")
      .addMockedMethod("saveResourceElements")
      .addMockedMethod("saveModel")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(abstractResourceService, "log", log)
    val resource = mock[MockResource]
    val resourceId = TestData.nextLong
    val resourceType = TestData.nextString
    val resourceRoot = TestData.resourceRoot(resourceId)
    val expectedModel = mock[MockModel]
    expecting {
      abstractResourceService.getResourceType andReturn resourceType times 2
      abstractResourceService.getResourceRoot(resourceType, resourceId) andReturn resourceRoot once()
      abstractResourceService.getCachedModel(resourceRoot) andReturn None once()
      resourceFactory.fromResourceRoot(resourceRoot) andReturn resource once()
      abstractResourceService.saveResourceElements(resource) once()
      resource.getModel andReturn expectedModel once()
      abstractResourceService.saveModel(resource, expectedModel) once()
    }
    whenExecuting(abstractResourceService, resourceFactory, resource) {
      val actualModel = abstractResourceService.getModel(resourceId)
      actualModel should be(expectedModel)
    }
  }

  "Abstract resource service" should "get model from cache" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
      .addMockedMethod("getCachedModel")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(abstractResourceService, "log", log)

    val resourceId = TestData.nextLong
    val resourceType = TestData.nextString
    val resourceRoot = TestData.resourceRoot(resourceId)
    val expectedModel = mock[MockModel]
    expecting {
      abstractResourceService.getResourceType andReturn resourceType times 2
      abstractResourceService.getResourceRoot(resourceType, resourceId) andReturn resourceRoot once()
      abstractResourceService.getCachedModel(resourceRoot) andReturn Some(expectedModel) once()
    }
    whenExecuting(abstractResourceService) {
      val actualModel = abstractResourceService.getModel(resourceId)
      actualModel should be(expectedModel)
    }
  }

  "Abstract resource service" should "get all models" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getModelOption")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(abstractResourceService, "log", log)

    val resourceType = TestData.nextString
    val resourceRootDmo1: ResourceRootDmo = TestData.resourceRootDmo
    val resourceRootDmo2: ResourceRootDmo = TestData.resourceRootDmo
    val resourceRootDmos = util.Arrays.asList(resourceRootDmo1, resourceRootDmo2)
    val modelOption1 = mock[Option[MockModel]]
    val modelOption2 = mock[Option[MockModel]]
    val model1 = mock[MockModel]
    val model2 = mock[MockModel]
    val expectedModels = List(model1, model2)
    expecting {
      abstractResourceService.getResourceType andReturn resourceType once()
      resourceRootDmoRepository.findByResourceType(resourceType) andReturn resourceRootDmos once()
      abstractResourceService.getModelOption(resourceRootDmo1.getResourceId) andReturn modelOption1 once()
      abstractResourceService.getModelOption(resourceRootDmo2.getResourceId) andReturn modelOption2 once()
      modelOption1.toList andReturn List(model1) once()
      modelOption2.toList andReturn List(model2) once()
    }
    whenExecuting(abstractResourceService, resourceRootDmoRepository, modelOption1, modelOption2) {
      abstractResourceService.getAllModels should be(expectedModels)
    }
  }

  "Abstract resource service" should "get cached model" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getModelClass")
      .addMockedMethod("getResourceType")
      .createMock())
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(abstractResourceService, "log", log)

    val resourceType = TestData.nextString
    val resourceRoot = TestData.resourceRoot
    val resourceModelId = TestData.resourceModelId
    val resourceModelDmo = mock[ResourceModelDmo]
    val body = TestData.nextString
    val modelClass = classOf[MockModel]
    val expectedModel = mock[MockModel]
    expecting {
      resourceModelIdFactory.resourceModelId(resourceRoot) andReturn resourceModelId once()
      resourceModelDmoRepository.findOne(resourceModelId) andReturn resourceModelDmo once()
      resourceModelDmo.getVersion andReturn resourceRoot.getVersion once()
      abstractResourceService.getResourceType andReturn resourceType once()
      resourceModelDmo.getBody andReturn body once()
      abstractResourceService.getModelClass andReturn modelClass once()
      modelSerializer.deserialize(body, modelClass) andReturn expectedModel once()
    }
    whenExecuting(abstractResourceService, resourceModelIdFactory, resourceModelDmoRepository, resourceModelDmo, modelSerializer) {
      val modelOption: Option[MockModel] = abstractResourceService.getCachedModel(resourceRoot)
      modelOption.orNull should be(expectedModel)
    }
  }

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
      val actualResourceRoot = resourceService.getResourceRoot(resourceType, resourceId)
      actualResourceRoot should be(expectedResourceRoot)
    }
  }
}
