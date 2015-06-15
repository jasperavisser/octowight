package nl.haploid.octowight.kafka

import java.lang

import kafka.consumer.{Consumer, ConsumerConfig}
import kafka.javaapi.consumer.ConsumerConnector
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConverters._

@Component
class KafkaConsumerFactory {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val consumerConfig: ConsumerConfig = null

  def createKafkaConsumer = {
    log.debug(s"Create kafka consumer for ${consumerConfig.zkConnect}")
    Consumer.createJavaConsumerConnector(consumerConfig)
  }

  def createStream(kafkaConsumer: ConsumerConnector, topic: String) = {
    val topicCountMap = Map[String, lang.Integer]((topic, 1)).asJava
    val streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap)
    streamsPerTopic.get(topic).get(0)
  }
}
