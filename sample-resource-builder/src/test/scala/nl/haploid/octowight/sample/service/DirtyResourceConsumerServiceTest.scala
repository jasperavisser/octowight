package nl.haploid.octowight.sample.service

import nl.haploid.octowight.kafka.{KafkaConsumer, KafkaConsumerFactory}
import nl.haploid.octowight.registry.data.ResourceIdentifier
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{JsonMapper, Mocked, Tested}
import org.springframework.test.util.ReflectionTestUtils

object DirtyResourceConsumerServiceTest {
  val Limit = 5
  val Topic = TestData.nextString
}

class DirtyResourceConsumerServiceTest extends AbstractTest {
  @Tested private[this] val dirtyResourceConsumerService: DirtyResourceConsumerService = null
  @Mocked private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  private[this] val topic = DirtyResourceConsumerServiceTest.Topic

  behavior of "Dirty resource consumer service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(dirtyResourceConsumerService, "topic", topic)
    ReflectionTestUtils.setField(dirtyResourceConsumerService, "limit", 5)
  }

  it should "consume resource identifiers" in {
    val kafkaConsumer = mock[KafkaConsumer]
    val message1 = TestData.nextString
    val message2 = TestData.nextString
    val messages = List(message1, message2)
    val resourceIdentifier1 = TestData.resourceIdentifier
    val resourceIdentifier2 = TestData.resourceIdentifier
    val expectedResourceIdentifiers = List(resourceIdentifier1, resourceIdentifier2)
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.nextMessages(DirtyResourceConsumerServiceTest.Limit) andReturn messages once()
      jsonMapper.deserialize(message1, classOf[ResourceIdentifier]) andReturn resourceIdentifier1 once()
      jsonMapper.deserialize(message2, classOf[ResourceIdentifier]) andReturn resourceIdentifier2 once()
    }
    whenExecuting(kafkaConsumerFactory, kafkaConsumer, jsonMapper) {
      val actualResourceIdentifiers = dirtyResourceConsumerService.consumeResourceIdentifiers()
      actualResourceIdentifiers should be(expectedResourceIdentifiers)
    }
  }

  it should "commit offsets" in {
    val kafkaConsumer = mock[KafkaConsumer]
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.commit andVoid() once()
    }
    whenExecuting(kafkaConsumerFactory, kafkaConsumer) {
      dirtyResourceConsumerService.commit()
    }
  }
}
