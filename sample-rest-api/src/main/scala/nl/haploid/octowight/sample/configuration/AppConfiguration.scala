package nl.haploid.octowight.sample.configuration

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
class AppConfiguration
