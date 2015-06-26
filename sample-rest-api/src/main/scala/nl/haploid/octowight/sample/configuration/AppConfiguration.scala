package nl.haploid.octowight.sample.configuration

import org.springframework.context.annotation.{Configuration, PropertySource, Bean}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@PropertySource(value = Array("file:./local.properties"), ignoreResourceNotFound = true)
@Configuration
class AppConfiguration
