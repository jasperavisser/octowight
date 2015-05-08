package nl.haploid.octowight.kafka

import kafka.consumer.{ConsumerTimeoutException, KafkaStream}
import kafka.message.MessageAndMetadata

class KakfaStreamIterator(stream: KafkaStream[Array[Byte], Array[Byte]]) extends Iterator[MessageAndMetadata[Array[Byte], Array[Byte]]] {
  val iterator = stream.iterator()

  var nextValue: MessageAndMetadata[Array[Byte], Array[Byte]] = _

  override def hasNext = {
    try {
      nextValue = iterator.next()
      true
    } catch {
      case e: ConsumerTimeoutException => false
    }
  }

  override def next() = nextValue
}
