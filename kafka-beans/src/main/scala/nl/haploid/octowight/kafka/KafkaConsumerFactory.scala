package nl.haploid.octowight.kafka

import kafka.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

// TODO: IT
@Component
class KafkaConsumerFactory {
  @Autowired private[this] val consumerConfig: ConsumerConfig = null

  def kafkaConsumer(topic: String): KafkaConsumer = new KafkaConsumer(consumerConfig, topic)
}
