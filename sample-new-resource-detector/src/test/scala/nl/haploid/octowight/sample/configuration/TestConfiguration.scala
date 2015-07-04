package nl.haploid.octowight.sample.configuration

import java.util.Properties

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.sample.TestData
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, FilterType}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val infrastructureHost = Option(System.getenv("INFRASTRUCTURE_HOST")).getOrElse("localhost")
    properties.setProperty("octowight.kafka.topic.events", TestData.topic)
    properties.setProperty("octowight.kafka.topic.resources.dirty", TestData.topic)
    properties.setProperty("octowight.kafka.hostname", infrastructureHost)
    properties.setProperty("octowight.kafka.port", "9092")
    properties.setProperty("octowight.kafka.consumer.timeout.ms", "2500")
    properties.setProperty("octowight.kafka.group.id", TestData.nextString)
    properties.setProperty("octowight.postgres.hostname", infrastructureHost)
    properties.setProperty("octowight.postgres.port", "5432")
    properties.setProperty("octowight.postgres.database", "postgres")
    properties.setProperty("octowight.postgres.username", "postgres")
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
@ComponentScan(basePackages = Array("nl.haploid.octowight"), excludeFilters = Array(
  new ComponentScan.Filter(`type` = FilterType.ASSIGNABLE_TYPE, value = Array(classOf[AppConfiguration]))
))
class TestConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
