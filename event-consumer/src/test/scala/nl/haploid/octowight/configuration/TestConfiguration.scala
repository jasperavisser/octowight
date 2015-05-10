package nl.haploid.octowight.configuration

import java.util.Properties

import nl.haploid.octowight.{JsonMapper, TestData}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val dockerHostIp = System.getenv("DOCKER_HOST_IP")
    properties.setProperty("octowight.postgres.hostname", dockerHostIp)
    properties.setProperty("octowight.postgres.port", "5432")
    properties.setProperty("octowight.postgres.database", "postgres")
    properties.setProperty("octowight.postgres.username", "postgres")
    properties.setProperty("octowight.kafka.hostname", dockerHostIp)
    properties.setProperty("octowight.kafka.port", "9092")
    properties.setProperty("octowight.kafka.consumer.timeout.ms", "2500")
    properties.setProperty("octowight.kafka.group.id", TestData.nextString)
    properties.setProperty("octowight.kafka.topic.events", TestData.topic)
    properties.setProperty("octowight.kafka.topic.resources.dirty", TestData.topic)
    properties.setProperty("octowight.registry.mongo.hostname", dockerHostIp)
    properties.setProperty("octowight.registry.mongo.port", "27017")
    properties.setProperty("octowight.registry.mongo.database", s"integration-test-${TestData.nextString}")
    properties.setProperty("octowight.zookeeper.hostname", dockerHostIp)
    properties.setProperty("octowight.zookeeper.port", "2181")
    configurer.setProperties(properties)
    configurer
  }
}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight")) class TestConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
