package nl.haploid.octowight.sample.configuration

import nl.haploid.octowight.JsonMapper
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling

object AppConfiguration {

  @Bean def propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}

@Configuration
@EnableScheduling
class AppConfiguration {

  @Bean def jsonMapper = new JsonMapper
}
