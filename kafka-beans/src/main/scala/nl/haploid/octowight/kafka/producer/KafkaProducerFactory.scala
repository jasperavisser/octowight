package nl.haploid.octowight.kafka.producer

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class KafkaProducerFactory {
  @Value("${octowight.kafka.hostname}") private[this] val kafkaHostname: String = null
  @Value("${octowight.kafka.port}") private[this] val kafkaPort: Int = 0

  def kafkaProducer = {
    val properties = new Properties
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"$kafkaHostname:$kafkaPort")
    properties.put(ProducerConfig.RETRIES_CONFIG, "3")
    properties.put(ProducerConfig.ACKS_CONFIG, "all")
    properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none")
    properties.put(ProducerConfig.BATCH_SIZE_CONFIG, "200")
    properties.put(ProducerConfig.BLOCK_ON_BUFFER_FULL_CONFIG, "true")
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    new KafkaProducer[String, String](properties)
  }
}
