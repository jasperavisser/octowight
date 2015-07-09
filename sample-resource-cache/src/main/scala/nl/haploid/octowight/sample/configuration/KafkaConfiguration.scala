package nl.haploid.octowight.sample.configuration

import java.util.Properties

import kafka.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class KafkaConfiguration {
  @Value("${octowight.kafka.consumer.timeout.ms}") private[this] val consumerTimeoutMs: Integer = null
  @Value("${octowight.kafka.group.id}") private[this] val kafkaGroupId: String = null
  @Value("${octowight.zookeeper.hostname}") private[this] val zookeeperHostname: String = null
  @Value("${octowight.zookeeper.port}") private[this] val zookeeperPort: Int = 0

  @Bean def consumerConfig: ConsumerConfig = {
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
}
