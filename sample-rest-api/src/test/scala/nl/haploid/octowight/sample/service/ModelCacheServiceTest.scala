package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.ModelSerializer
import nl.haploid.octowight.registry.repository._
import nl.haploid.octowight.sample._
import nl.haploid.octowight.{Mocked, Tested}
import org.easymock.EasyMock
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

class ModelCacheServiceTest extends AbstractTest {
  @Tested private[this] val modelCacheService: ModelCacheService[MockModel, MockResource] = null
  @Mocked private[this] val modelSerializer: ModelSerializer[MockModel] = null
  @Mocked private[this] val resourceModelDmoFactory: ResourceModelDmoFactory = null
  @Mocked private[this] val resourceModelDmoRepository: ResourceModelDmoRepository = null
  @Mocked private[this] val resourceModelIdFactory: ResourceModelIdFactory = null

  override def beforeEach() = {
    super.beforeEach()
    val log = EasyMock.createMock(classOf[Logger])
    ReflectionTestUtils.setField(modelCacheService, "log", log)
  }

  "Model cache service" should "get a model" in {
    val resourceRoot = TestData.resourceRoot
    val resourceModelId = TestData.resourceModelId
    val resourceModelDmo = mock[ResourceModelDmo]
    val body = TestData.nextString
    val modelClass = classOf[MockModel]
    val expectedModel = mock[MockModel]
    expecting {
      resourceModelIdFactory.resourceModelId(resourceRoot) andReturn resourceModelId once()
      resourceModelDmoRepository.findByIdAndVersion(resourceModelId, resourceRoot.getVersion) andReturn resourceModelDmo once()
      resourceModelDmo.getBody andReturn body once()
      modelSerializer.deserialize(body, modelClass) andReturn expectedModel once()
    }
    whenExecuting(resourceModelIdFactory, resourceModelDmoRepository, resourceModelDmo, modelSerializer) {
      val modelOption: Option[MockModel] = modelCacheService.get(resourceRoot, modelClass)
      modelOption.orNull should be(expectedModel)
    }
  }

  "Model cache service" should "put a new model" in {
    val resource = mock[MockResource]
    val model = mock[MockModel]
    val body = TestData.nextString
    val resourceModelId = mock[ResourceModelId]
    val resourceModelDmo = mock[ResourceModelDmo]
    expecting {
      resource.getType andReturn TestData.nextString once()
      resource.getId andReturn TestData.nextLong once()
      modelSerializer.serialize(model) andReturn body once()
      resourceModelIdFactory.resourceModelId(resource) andReturn resourceModelId once()
      resourceModelDmoRepository.findOne(resourceModelId) andReturn null once()
      resourceModelDmoFactory.fromResourceAndBody(resource, body) andReturn resourceModelDmo once()
      resourceModelDmoRepository.save(resourceModelDmo) andReturn resourceModelDmo once()
    }
    whenExecuting(resource, modelSerializer, resourceModelIdFactory, resourceModelDmoRepository, resourceModelDmoFactory) {
      modelCacheService.put(resource, model)
    }
  }
}
