package nl.haploid.octowight.kafka

import kafka.consumer.KafkaStream
import kafka.message.MessageAndMetadata

import scala.util.{Success, Try}

class KafkaStreamIterator(stream: KafkaStream[Array[Byte], Array[Byte]]) extends Iterator[MessageAndMetadata[Array[Byte], Array[Byte]]] {
  val iterator = stream.iterator()

  var nextValue: MessageAndMetadata[Array[Byte], Array[Byte]] = _

  override def hasNext = {
    val result = Try(iterator.next())
    result match {
      case Success(value) =>
        nextValue = value
        true
      case _ => false
    }
  }

  override def next() = nextValue
}
