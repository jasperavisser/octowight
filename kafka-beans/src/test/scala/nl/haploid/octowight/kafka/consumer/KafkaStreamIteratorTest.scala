package nl.haploid.octowight.kafka.consumer

import kafka.consumer.{ConsumerIterator, KafkaStream}
import kafka.message.MessageAndMetadata
import nl.haploid.octowight.kafka.AbstractTest

class KafkaStreamIteratorTest extends AbstractTest {

  behavior of "Kafka stream iterator"

  it should "iterate over a kafka stream" in {
    val stream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val iterator = mock[ConsumerIterator[Array[Byte], Array[Byte]]]
    val messageAndMetadata1 = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]
    val messageAndMetadata2 = mock[MessageAndMetadata[Array[Byte], Array[Byte]]]
    val expectedMessageAndMetadatas = List(messageAndMetadata1, messageAndMetadata2)
    val kafkaStreamIterator = new KafkaStreamIterator(stream)
    expecting {
      stream.iterator andReturn iterator once()
      iterator.next() andReturn messageAndMetadata1 once()
      iterator.next() andReturn messageAndMetadata2 once()
      iterator.next() andThrow new NoSuchElementException once()
    }
    whenExecuting(stream, iterator) {
      val actualMessageAndMetadatas = kafkaStreamIterator.map(m => m).toList
      actualMessageAndMetadatas should be(expectedMessageAndMetadatas)
    }
  }
}
