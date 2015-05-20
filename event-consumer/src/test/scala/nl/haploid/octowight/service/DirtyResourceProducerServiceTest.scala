package nl.haploid.octowight.service

import java.util.concurrent.Future

import nl.haploid.octowight._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.EasyMock
import org.slf4j.Logger
import org.springframework.test.util.ReflectionTestUtils

class DirtyResourceProducerServiceTest extends AbstractTest {
  @Tested private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null
  @Mocked private[this] val kafkaProducer: KafkaProducer[String, String] = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(dirtyResourceProducerService, "topic", TestData.topic)
    ReflectionTestUtils.setField(dirtyResourceProducerService, "log", mock[Logger])
  }

  "Dirty resource producer" should "send dirty resource" in {
    val expectedFuture = mock[Future[RecordMetadata]]
    val resourceRoot = TestData.resourceRoot(TestData.nextLong)
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
