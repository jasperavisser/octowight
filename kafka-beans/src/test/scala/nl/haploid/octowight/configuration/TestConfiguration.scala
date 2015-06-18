package nl.haploid.octowight.configuration

import java.util.Properties

import kafka.consumer.ConsumerConfig
import nl.haploid.octowight.TestData
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val dockerHostIp = System.getenv("DOCKER_HOST_IP")
    properties.setProperty("octowight.kafka.limit.resources.dirty", "10")
    properties.setProperty("octowight.kafka.topic.resources.dirty", TestData.nextString)
    properties.setProperty("octowight.kafka.topic.resources.built", TestData.nextString)
    properties.setProperty("octowight.kafka.hostname", dockerHostIp)
    properties.setProperty("octowight.kafka.port", "9092")
    properties.setProperty("octowight.kafka.consumer.timeout.ms", "2500")
    properties.setProperty("octowight.kafka.group.id", TestData.nextString)
    properties.setProperty("octowight.zookeeper.hostname", dockerHostIp)
    properties.setProperty("octowight.zookeeper.port", "2181")
    configurer.setProperties(properties)
    configurer
  }
}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class TestConfiguration {
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
}
