package nl.haploid.octowight.kafka

import kafka.consumer.{ConsumerTimeoutException, KafkaStream}
import kafka.message.MessageAndMetadata

import scala.util.{Failure, Success, Try}

class KakfaStreamIterator(stream: KafkaStream[Array[Byte], Array[Byte]]) extends Iterator[MessageAndMetadata[Array[Byte], Array[Byte]]] {
  val iterator = stream.iterator()

  var nextValue: MessageAndMetadata[Array[Byte], Array[Byte]] = _

  override def hasNext = {
    val result = Try(iterator.next())
    result match {
      case Success(value) =>
        nextValue = value
        true
      case Failure(e: ConsumerTimeoutException) => false
    }
  }

  override def next() = nextValue
}
