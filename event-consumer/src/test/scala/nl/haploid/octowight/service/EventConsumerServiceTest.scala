package nl.haploid.octowight.service

import nl.haploid.octowight._
import nl.haploid.octowight.kafka.consumer.{KafkaConsumer, KafkaConsumerFactory}
import org.springframework.test.util.ReflectionTestUtils

class EventConsumerServiceTest extends AbstractTest {
  @Tested private[this] val eventConsumerService: EventConsumerService = null
  @Mocked private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  private[this] val topic = TestData.topic

  behavior of "Event consumer service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(eventConsumerService, "topic", topic)
  }

  it should "consume a message" in {
    val kafkaConsumer = mock[KafkaConsumer]
    val expectedEvent = TestData.atomChangeEvent("rick")
    val message = TestData.message
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.nextMessage andReturn message once()
      jsonMapper.deserialize(message, classOf[AtomChangeEvent]) andReturn expectedEvent once()
    }
    whenExecuting(kafkaConsumerFactory, jsonMapper, kafkaConsumer) {
      val actualEvent = eventConsumerService.consumeEvent
      actualEvent should be(expectedEvent)
    }
  }

  it should "consume multiple messages" in {
    val kafkaConsumer = mock[KafkaConsumer]
    val event1 = TestData.atomChangeEvent(TestData.nextString)
    val event2 = TestData.atomChangeEvent(TestData.nextString)
    val message1 = TestData.message
    val message2 = TestData.message
    val messages = Iterable(message1, message2)
    val expectedEvents = Set(event1, event2)
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.nextMessages(100) andReturn messages once()
      jsonMapper.deserialize(message1, classOf[AtomChangeEvent]) andReturn event1 once()
      jsonMapper.deserialize(message2, classOf[AtomChangeEvent]) andReturn event2 once()
    }
    whenExecuting(kafkaConsumerFactory, kafkaConsumer, jsonMapper) {
      val actualEvents = eventConsumerService.consumeDistinctEvents()
      actualEvents should be(expectedEvents)
    }
  }

  it should "commit stream offset" in {
    val kafkaConsumer = mock[KafkaConsumer]
    expecting {
      kafkaConsumerFactory.kafkaConsumer(topic) andReturn kafkaConsumer once()
      kafkaConsumer.commit once()
    }
    whenExecuting(kafkaConsumerFactory, kafkaConsumer) {
      eventConsumerService.commit()
    }
  }
}
