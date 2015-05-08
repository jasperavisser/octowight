package nl.haploid.octowight.service

import java.util.concurrent.Future

import nl.haploid.octowight._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.EasyMock
import org.springframework.test.util.ReflectionTestUtils

class DirtyResourceProducerServiceTest extends AbstractTest {
  @Tested private val dirtyResourceProducerService = new DirtyResourceProducerService
  @Mocked private val kafkaProducer: KafkaProducer[String, String] = null
  @Mocked private val jsonMapper: JsonMapper = null

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(dirtyResourceProducerService, "topic", TestData.topic)
  }

  "Dirty resource producer" should "send dirty resource" in {
    val expectedFuture = mock[Future[RecordMetadata]]
    val resourceRoot = TestData.resourceRoot(451l)
    val message = "joy"
    expecting {
      jsonMapper.serialize(resourceRoot) andReturn message once()
      kafkaProducer.send(EasyMock.anyObject[ProducerRecord[String, String]]) andReturn expectedFuture once()
    }
    whenExecuting(jsonMapper, kafkaProducer) {
      val actualFuture = dirtyResourceProducerService.sendDirtyResource(resourceRoot)
      actualFuture should be(expectedFuture)
    }
  }
}
