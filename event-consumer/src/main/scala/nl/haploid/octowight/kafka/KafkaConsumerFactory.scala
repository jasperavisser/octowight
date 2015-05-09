package nl.haploid.octowight.kafka

import java.util

import kafka.consumer.{Consumer, ConsumerConfig}
import kafka.javaapi.consumer.ConsumerConnector
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KafkaConsumerFactory {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val consumerConfig: ConsumerConfig = null

  def createKafkaConsumer = {
    log.debug(s"Create kafka consumer for ${consumerConfig.zkConnect}")
    Consumer.createJavaConsumerConnector(consumerConfig)
  }

  def createStream(kafkaConsumer: ConsumerConnector, topic: String) = {
    val topicCountMap = new util.HashMap[String, Integer]
    topicCountMap.put(topic, 1)
    val streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap)
    streamsPerTopic.get(topic).get(0)
  }
}
