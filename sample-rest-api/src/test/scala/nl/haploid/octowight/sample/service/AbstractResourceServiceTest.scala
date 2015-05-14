package nl.haploid.octowight.sample.service

import java.util

import nl.haploid.octowight.registry.data._
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample._
import nl.haploid.octowight.{Mocked, Tested}
import org.easymock.EasyMock
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

class AbstractResourceServiceTest extends AbstractTest {
  @Tested private[this] val resourceService: MockResourceService = null
  @Mocked private[this] val modelCacheService: ModelCacheService[MockModel, MockResource] = null
  @Mocked private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Mocked private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Mocked private[this] val resourceFactory: ResourceFactory[MockResource] = null

  override def beforeEach() = {
    super.beforeEach()
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(resourceService, "log", log)
  }

  "Abstract resource service" should "get model from origin" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getModelClass")
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
      .addMockedMethod("saveResourceElements")
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
      abstractResourceService.getModelClass andReturn classOf[MockModel] once()
      modelCacheService.get(resourceRoot, classOf[MockModel]) andReturn None once()
      resourceFactory.fromResourceRoot(resourceRoot) andReturn Some(resource) once()
      abstractResourceService.saveResourceElements(resource) once()
      resource.getModel andReturn expectedModel once()
      modelCacheService.put(resource, expectedModel) once()
    }
    whenExecuting(abstractResourceService, resourceFactory, resource, modelCacheService) {
      val actualModel = abstractResourceService.getModel(resourceId)
      actualModel should be(expectedModel)
    }
  }

  "Abstract resource service" should "get model from cache" in {
    val abstractResourceService = withMocks(EasyMock.createMockBuilder(classOf[MockResourceService])
      .addMockedMethod("getModelClass")
      .addMockedMethod("getResourceType")
      .addMockedMethod("getResourceRoot")
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
      abstractResourceService.getModelClass andReturn classOf[MockModel] once()
      modelCacheService.get(resourceRoot, classOf[MockModel]) andReturn Some(expectedModel) once()
    }
    whenExecuting(abstractResourceService, modelCacheService) {
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
      resourceRootDmoRepository.findByResourceTypeAndTombstone(resourceType, tombstone = false) andReturn resourceRootDmos once()
      abstractResourceService.getModelOption(resourceRootDmo1.getResourceId) andReturn modelOption1 once()
      abstractResourceService.getModelOption(resourceRootDmo2.getResourceId) andReturn modelOption2 once()
      modelOption1.toList andReturn List(model1) once()
      modelOption2.toList andReturn List(model2) once()
    }
    whenExecuting(abstractResourceService, resourceRootDmoRepository, modelOption1, modelOption2) {
      abstractResourceService.getAllModels should be(expectedModels)
    }
  }

  "Abstract resource service" should "get resource root" in {
    val resourceId = TestData.nextLong
    val resourceType = TestData.nextString
    val resourceRootDmo = TestData.resourceRootDmo
    val expectedResourceRoot = ResourceRoot(resourceRootDmo)
    expecting {
      resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId) andReturn resourceRootDmo once()
    }
    whenExecuting(resourceRootDmoRepository) {
      val actualResourceRoot = resourceService.getResourceRoot(resourceType, resourceId)
      actualResourceRoot should be(expectedResourceRoot)
    }
  }

  "Abstract resource service" should "save resource elements" in {
    val atom1 = TestData.atom
    val atom2 = TestData.atom
    val resource = TestData.mockResource(Set(atom1, atom2))
    val resourceElementDmo1 = ResourceElementDmo(resource, atom1)
    val resourceElementDmo2 = ResourceElementDmo(resource, atom2)
    expecting {
      resourceElementDmoRepository.deleteByResourceTypeAndResourceId(resource.getType, resource.getId) andReturn 0 once()
      resourceElementDmoRepository.save(resourceElementDmo1) andReturn mock[ResourceElementDmo] once()
      resourceElementDmoRepository.save(resourceElementDmo2) andReturn mock[ResourceElementDmo] once()
    }
    whenExecuting(resourceElementDmoRepository) {
      resourceService.saveResourceElements(resource)
    }
  }

  "Abstract resource service" should "tombstone a resource" in {
    val resourceRoot = TestData.resourceRoot
    val resourceRootDmo = TestData.resourceRootDmo
    val tombstonedResourceRootDmo = TestData.resourceRootDmo
    tombstonedResourceRootDmo.setResourceId(resourceRootDmo.getResourceId)
    tombstonedResourceRootDmo.setTombstone(true)
    expecting {
      resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceRoot.getResourceType, resourceRoot.getResourceId) andReturn resourceRootDmo once()
      resourceRootDmoRepository.save(tombstonedResourceRootDmo) andReturn tombstonedResourceRootDmo once()
    }
    whenExecuting(resourceRootDmoRepository) {
      resourceService.tombstoneResource(resourceRoot)
    }
  }
}
