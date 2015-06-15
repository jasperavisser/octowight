package nl.haploid.octowight.service

import _root_.kafka.consumer.{ConsumerIterator, ConsumerTimeoutException, KafkaStream}
import _root_.kafka.javaapi.consumer.ConsumerConnector
import _root_.kafka.message.MessageAndMetadata
import nl.haploid.octowight._
import nl.haploid.octowight.kafka.KafkaConsumerFactory
import org.easymock.EasyMock

class EventConsumerServiceTest extends AbstractTest {
  @Tested val eventConsumerService = EasyMock.createMockBuilder(classOf[EventConsumerService])
    .addMockedMethod("consumerConnector")
    .addMockedMethod("stream")
    .createMock()
  @Mocked private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  override def beforeEach() = {
    super.beforeEach()
    EasyMock.reset(eventConsumerService)
  }

  "Event consumer" should "consume a message" in {
    val stream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val iterator = mock[ConsumerIterator[Array[Byte], Array[Byte]]]
    val messageAndMetaData = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]

    val expectedEvent = TestData.atomChangeEvent("rick")
    val message = TestData.message
    expecting {
      eventConsumerService.stream andReturn stream once()
      stream.iterator andReturn iterator once()
      iterator.next andReturn messageAndMetaData once()
      messageAndMetaData.message andReturn message.getBytes once()
      jsonMapper.deserialize(message, classOf[AtomChangeEvent]) andReturn expectedEvent once()
    }
    whenExecuting(eventConsumerService, stream, iterator, messageAndMetaData, jsonMapper) {
      val actualEvent = eventConsumerService.consumeEvent
      actualEvent should be(expectedEvent)
    }
  }

  "Event consumer" should "consume multiple messages" in {
    val stream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val iterator = mock[ConsumerIterator[Array[Byte], Array[Byte]]]
    val messageAndMetaData = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]

    val event1 = TestData.atomChangeEvent("carol")
    val event2 = TestData.atomChangeEvent("daryl")
    val message1 = TestData.message
    val message2 = TestData.message
    val expectedEvents = Set(event1, event2)
    expecting {
      eventConsumerService.stream andReturn stream once()
      stream.iterator andReturn iterator once()
      iterator.next andReturn messageAndMetaData once()
      messageAndMetaData.message andReturn message1.getBytes once()
      jsonMapper.deserialize(message1, classOf[AtomChangeEvent]) andReturn event1 once()
      iterator.next andReturn messageAndMetaData once()
      messageAndMetaData.message andReturn message2.getBytes once()
      jsonMapper.deserialize(message2, classOf[AtomChangeEvent]) andReturn event2 once()
      iterator.next andThrow new ConsumerTimeoutException once()
    }
    whenExecuting(eventConsumerService, stream, iterator, messageAndMetaData, jsonMapper) {
      val actualEvents = eventConsumerService.consumeDistinctEvents()
      actualEvents should be(expectedEvents)
    }
  }

  "Event consumer" should "commit stream offset" in {
    val kafkaConsumer = mock[ConsumerConnector]
    expecting {
      eventConsumerService.consumerConnector andReturn kafkaConsumer once()
      kafkaConsumer.commitOffsets() once()
    }
    whenExecuting(kafkaConsumer, eventConsumerService) {
      eventConsumerService.commit()
    }
  }
}
