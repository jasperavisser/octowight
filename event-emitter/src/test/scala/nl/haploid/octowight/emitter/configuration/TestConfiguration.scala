package nl.haploid.octowight.emitter.configuration

import java.util.Properties

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.emitter.TestData
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val infrastructureHost = Option(System.getenv("INFRASTRUCTURE_HOST")).getOrElse("localhost")
    properties.setProperty("octowight.kafka.topic.events", TestData.topic)
    properties.setProperty("octowight.kafka.hostname", infrastructureHost)
    properties.setProperty("octowight.kafka.port", "9092")
    configurer.setProperties(properties)
    configurer
  }
}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class TestConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
