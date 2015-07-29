package nl.haploid.octowight.consumer.service

import java.util.concurrent.Future

import nl.haploid.octowight._
import nl.haploid.octowight.consumer.{AbstractTest, TestData}
import nl.haploid.octowight.kafka.producer.KafkaProducerFactory
import nl.haploid.octowight.registry.data.ResourceIdentifier
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.EasyMock
import org.springframework.test.util.ReflectionTestUtils

class DirtyResourceProducerServiceTest extends AbstractTest {
  @Tested private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null
  @Mocked private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  behavior of "Dirty resource producer service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(dirtyResourceProducerService, "topic", TestData.nextString)
  }

  it should "send a dirty resource" in {
    val kafkaProducer = mock[KafkaProducer[String, String]]
    val expectedFuture = mock[Future[RecordMetadata]]
    val resourceRoot = TestData.resourceRoot(TestData.nextLong)
    val resourceIdentifier = new ResourceIdentifier(collection = resourceRoot.resourceCollection, id = resourceRoot.resourceId)
    val message = TestData.nextString
    expecting {
      kafkaProducerFactory.kafkaProducer andReturn kafkaProducer once()
      jsonMapper.serialize(resourceIdentifier) andReturn message once()
      kafkaProducer.send(EasyMock.anyObject[ProducerRecord[String, String]]) andReturn expectedFuture once()
    }
    whenExecuting(jsonMapper, kafkaProducerFactory, kafkaProducer) {
      val actualFuture = dirtyResourceProducerService.sendDirtyResource(resourceRoot)
      actualFuture should be(expectedFuture)
    }
  }
}
