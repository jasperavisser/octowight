package nl.haploid.octowight.kafka

import java.lang

import kafka.consumer.{Consumer, ConsumerConfig, KafkaStream}
import kafka.javaapi.consumer.ConsumerConnector
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

// TODO: re-use in other projects!
// TODO: IT
class KafkaConsumer(val consumerConfig: ConsumerConfig, val topic: String) {

  protected[this] lazy val log = LoggerFactory.getLogger(getClass)

  lazy val stream: KafkaStream[Array[Byte], Array[Byte]] = {
    log.debug(s"Create new stream for $topic}")
    val topicCountMap = Map[String, lang.Integer]((topic, 1)).asJava
    val streamsPerTopic = consumerConnector.createMessageStreams(topicCountMap)
    streamsPerTopic.get(topic).get(0)
  }

  lazy val consumerConnector: ConsumerConnector = {
    log.debug(s"Create kafka consumer for ${consumerConfig.zkConnect}")
    Consumer.createJavaConsumerConnector(consumerConfig)
  }

  def commit() = consumerConnector.commitOffsets()

  def nextMessage = new String(stream.iterator().next().message())

  def nextMessages(limit: Int): Iterable[String] = {
    val events = new KafkaStreamIterator(stream)
      .take(limit)
      .map(m => new String(m.message()))
      .toIterable
    log.debug(s"Consumed ${events.size} events")
    events
  }

  def shutdown() = consumerConnector.shutdown()
}
