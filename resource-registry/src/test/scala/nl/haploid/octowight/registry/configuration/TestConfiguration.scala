package nl.haploid.octowight.registry.configuration

import java.util.Properties

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.registry.TestData
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    configurer.setProperties(getTestProperties)
    configurer
  }

  private def getTestProperties = {
    val properties = new Properties
    val dockerHostIp = System.getenv("DOCKER_HOST_IP")
    properties.setProperty("octowight.registry.mongo.hostname", dockerHostIp)
    properties.setProperty("octowight.registry.mongo.port", "27017")
    properties.setProperty("octowight.registry.mongo.database", s"integration-test-${TestData.nextString}")
    properties
  }
}

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight.registry"))
class TestConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
