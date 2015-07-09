package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.{ResourceIdentifier, ResourceMessage}
import nl.haploid.octowight.sample.AbstractTest
import nl.haploid.octowight.sample.data.{CaptainModel, JsonModelSerializer}
import nl.haploid.octowight.sample.repository.{CaptainDmo, CaptainDmoRepository}
import nl.haploid.octowight.{Mocked, Tested}


class CaptainCacheServiceTest extends AbstractTest {
  @Tested private[this] val captainCacheService: CaptainCacheService = null
  @Mocked private[this] val serializer: JsonModelSerializer[CaptainModel] = null
  @Mocked private[this] val resourceDmoRepository: CaptainDmoRepository = null

  behavior of "Captain cache service"

  it should "save message" in {

    val resource = mock[CaptainModel]
    val identifier = new ResourceIdentifier(collection = "xxx", id = 123L)
    val message = new ResourceMessage(resourceIdentifier = identifier, model = "x", tombstone = false)
    val captainDmo: CaptainDmo = new CaptainDmo(id = identifier.id, model = resource, tombstone = false)

    expecting {
      serializer.deserialize("x", classOf[CaptainModel]) andReturn resource once()
      resourceDmoRepository.save(captainDmo) andReturn captainDmo
    }
    whenExecuting(serializer, resourceDmoRepository) {
      captainCacheService.saveResource(message)
    }
  }

  it should "save empty message when tombstone is set" in {

    val resource = mock[CaptainModel]
    val identifier = new ResourceIdentifier(collection = "xxx", id = 123L)
    val message = new ResourceMessage(resourceIdentifier = identifier, model = "x", tombstone = true)
    val captainDmo: CaptainDmo = new CaptainDmo(id = identifier.id, model = null, tombstone = true)

    expecting {
      resourceDmoRepository.save(captainDmo) andReturn captainDmo
    }
    whenExecuting(serializer, resourceDmoRepository) {
      captainCacheService.saveResource(message)
    }
  }

}
