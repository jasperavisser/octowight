package nl.haploid.octowight.sample.service

import nl.haploid.octowight.kafka.{KafkaConsumer, KafkaConsumerFactory}
import nl.haploid.octowight.registry.data.ResourceMessage
import nl.haploid.octowight.sample.AbstractTest
import nl.haploid.octowight.{JsonMapper, Mocked, Tested}
import org.springframework.test.util.ReflectionTestUtils


class ResourceConsumerServiceTest extends AbstractTest {
  @Tested private[this] val dirtyResourceConsumerService: ResourceConsumerService = null
  @Mocked private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  private[this] val topic = "jsbged"
  private[this] val limit = 7

  behavior of "Resource consumer service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(dirtyResourceConsumerService, "topic", topic)
    ReflectionTestUtils.setField(dirtyResourceConsumerService, "limit", limit)
  }

  it should "consume resource messages" in {
    val kafkaConsumer = mock[KafkaConsumer]
    val message1 = "message234"
    val message2 = "message464"
    val messages = List(message1, message2)
    val resourceMessage1 = mock[ResourceMessage]
    val resourceMessage2 = mock[ResourceMessage]
    val expectedResourceIdentifiers = List(resourceMessage1, resourceMessage2)
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.nextMessages(limit) andReturn messages once()
      jsonMapper.deserialize(message1, classOf[ResourceMessage]) andReturn resourceMessage1 once()
      jsonMapper.deserialize(message2, classOf[ResourceMessage]) andReturn resourceMessage2 once()
    }
    whenExecuting(kafkaConsumerFactory, kafkaConsumer, jsonMapper) {
      val actualResourceIdentifiers = dirtyResourceConsumerService.consumeResourceMessages()
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
