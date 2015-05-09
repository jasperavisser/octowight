package nl.haploid.octowight.sample.configuration

import java.util.Properties

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, FilterType}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.octowight"), excludeFilters = Array(
  new ComponentScan.Filter(`type` = FilterType.REGEX, pattern = Array("nl.haploid.octowight.registry.*"))
))
object TestConfiguration {

  @Bean def propertyPlaceholderConfigurer = {
    val configurer = new PropertySourcesPlaceholderConfigurer
    val properties = new Properties
    val dockerHostIp = System.getenv("DOCKER_HOST_IP")
    properties.setProperty("octowight.postgres.hostname", dockerHostIp)
    properties.setProperty("octowight.postgres.port", "5432")
    properties.setProperty("octowight.postgres.database", "postgres")
    properties.setProperty("octowight.postgres.username", "postgres")
    configurer.setProperties(properties)
    configurer
  }
}

class TestConfiguration