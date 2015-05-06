package nl.haploid.octowight.configuration

import nl.haploid.octowight.JsonMapper
import nl.haploid.octowight.TestData
import org.apache.kafka.common.config.ConfigDef
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import java.util.Properties

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
