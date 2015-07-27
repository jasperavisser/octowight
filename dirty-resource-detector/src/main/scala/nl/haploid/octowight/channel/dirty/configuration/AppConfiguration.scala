package nl.haploid.octowight.channel.dirty.configuration

import nl.haploid.octowight.JsonMapper
import org.springframework.context.annotation.{Bean, Configuration, PropertySource}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
@EnableScheduling
@PropertySource(value = Array("file:./local.properties"), ignoreResourceNotFound = true)
class AppConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
