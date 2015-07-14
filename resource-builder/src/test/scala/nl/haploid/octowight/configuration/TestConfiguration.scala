package nl.haploid.octowight.configuration

import java.util.Properties

import nl.haploid.octowight.{JsonMapper, TestData}
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val infrastructureHost = Option(System.getenv("INFRASTRUCTURE_HOST")).getOrElse("localhost")
    properties.setProperty("octowight.kafka.limit.resources.dirty", "10")
    properties.setProperty("octowight.kafka.topic.resources.dirty", TestData.nextString)
    properties.setProperty("octowight.kafka.topic.resources.built", TestData.nextString)
    properties.setProperty("octowight.kafka.hostname", infrastructureHost)
    properties.setProperty("octowight.kafka.port", "9092")
    properties.setProperty("octowight.kafka.consumer.timeout.ms", "2500")
    properties.setProperty("octowight.kafka.group.id", TestData.nextString)
    properties.setProperty("octowight.registry.mongo.hostname", infrastructureHost)
    properties.setProperty("octowight.registry.mongo.port", "27017")
    properties.setProperty("octowight.registry.mongo.database", s"integration-test-${TestData.nextString}")
    properties.setProperty("octowight.zookeeper.hostname", infrastructureHost)
    properties.setProperty("octowight.zookeeper.port", "2181")
    configurer.setProperties(properties)
    configurer
  }
}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class TestConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
