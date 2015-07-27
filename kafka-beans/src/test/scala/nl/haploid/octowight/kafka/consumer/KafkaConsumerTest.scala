package nl.haploid.octowight.kafka.consumer

import kafka.consumer.{ConsumerIterator, KafkaStream}
import kafka.javaapi.consumer.ConsumerConnector
import kafka.message.MessageAndMetadata
import nl.haploid.octowight.kafka.{AbstractTest, TestData}
import org.easymock.EasyMock

class KafkaConsumerTest extends AbstractTest {

  behavior of "Kafka consumer"

  it should "get the next N messages" in {
    val kafkaConsumer = EasyMock.createMockBuilder(classOf[KafkaConsumer])
      .addMockedMethod("stream")
      .createMock()
    val stream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val iterator = mock[ConsumerIterator[Array[Byte], Array[Byte]]]
    val messageAndMetadata1 = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]
    val messageAndMetadata2 = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]
    val message1 = TestData.nextString
    val message2 = TestData.nextString
    expecting {
      kafkaConsumer.stream andReturn stream once()
      stream.iterator() andReturn iterator once()
      iterator.next() andReturn messageAndMetadata1 once()
      iterator.next() andReturn messageAndMetadata2 once()
      iterator.next() andThrow new NoSuchElementException() once()
      messageAndMetadata1.message andReturn message1.getBytes once()
      messageAndMetadata2.message andReturn message2.getBytes once()
    }
    whenExecuting(kafkaConsumer, stream, iterator, messageAndMetadata1, messageAndMetadata2) {
      kafkaConsumer.nextMessages(limit = 10) should have size 2
    }
  }

  it should "get the next message" in {
    val kafkaConsumer = EasyMock.createMockBuilder(classOf[KafkaConsumer])
      .addMockedMethod("stream")
      .createMock()
    val stream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val iterator = mock[ConsumerIterator[Array[Byte], Array[Byte]]]
    val messageAndMetadata = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]
    val message = TestData.nextString
    expecting {
      kafkaConsumer.stream andReturn stream once()
      stream.iterator() andReturn iterator once()
      iterator.next() andReturn messageAndMetadata once()
      messageAndMetadata.message andReturn message.getBytes once()
    }
    whenExecuting(kafkaConsumer, stream, iterator, messageAndMetadata) {
      kafkaConsumer.nextMessage should be(message)
    }
  }

  it should "commit offsets" in {
    val kafkaConsumer = EasyMock.createMockBuilder(classOf[KafkaConsumer])
      .addMockedMethod("consumerConnector")
      .createMock()
    val consumerConnector = mock[ConsumerConnector]
    expecting {
      kafkaConsumer.consumerConnector andReturn consumerConnector once()
      consumerConnector.commitOffsets()
    }
    whenExecuting(kafkaConsumer, consumerConnector) {
      kafkaConsumer.commit()
    }
  }
}
