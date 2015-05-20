package nl.haploid.octowight.sample.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

class AppConfiguration