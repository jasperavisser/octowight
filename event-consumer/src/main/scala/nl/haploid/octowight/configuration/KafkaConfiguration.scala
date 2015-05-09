package nl.haploid.octowight.configuration

import java.util.Properties

import kafka.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class KafkaConfiguration {
  @Value("${octowight.kafka.hostname}") private[this] val kafkaHostname: String = null
  @Value("${octowight.kafka.port}") private[this] val kafkaPort: Int = 0
  @Value("${octowight.kafka.consumer.timeout.ms}") private[this] val consumerTimeoutMs: Integer = null
  @Value("${octowight.kafka.group.id}") private[this] val kafkaGroupId: String = null
  @Value("${octowight.zookeeper.hostname}") private[this] val zookeeperHostname: String = null
  @Value("${octowight.zookeeper.port}") private[this] val zookeeperPort: Int = 0

  @Bean def consumerConfig = {
    val properties = new Properties
    properties.put("zookeeper.connect", s"$zookeeperHostname:$zookeeperPort")
    properties.put("group.id", kafkaGroupId)
    properties.put("zookeeper.session.timeout.ms", "2500")
    properties.put("zookeeper.sync.time.ms", "200")
    properties.put("auto.commit.enable", "false")
    properties.put("auto.offset.reset", "smallest")
    properties.put("consumer.timeout.ms", consumerTimeoutMs.toString)
    new ConsumerConfig(properties)
  }

  @Bean def kafkaProducer = {
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
