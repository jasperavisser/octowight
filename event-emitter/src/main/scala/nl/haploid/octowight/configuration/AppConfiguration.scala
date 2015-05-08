package nl.haploid.octowight.configuration

import nl.haploid.octowight.JsonMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
@EnableScheduling
class AppConfiguration {

  @Bean def jsonMapper: JsonMapper = new JsonMapper
}
