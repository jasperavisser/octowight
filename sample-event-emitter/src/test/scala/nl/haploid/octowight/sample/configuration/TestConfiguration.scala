package nl.haploid.octowight.sample.configuration

import java.util.Properties

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.configuration.AppConfiguration
import nl.haploid.octowight.sample.TestData
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, FilterType}
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
    properties.setProperty("octowight.kafka.topic.events", TestData.topic)
    properties.setProperty("octowight.kafka.hostname", dockerHostIp)
    properties.setProperty("octowight.kafka.port", "9092")
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
