package nl.haploid.octowight.configuration

import java.util.Properties

import nl.haploid.octowight.{JsonMapper, TestData}
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, FilterType}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = {
    val configurer: PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
    val properties: Properties = new Properties
    val dockerHostIp: String = System.getenv("DOCKER_HOST_IP")
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

  @Bean def jsonMapper: JsonMapper = new JsonMapper
}
