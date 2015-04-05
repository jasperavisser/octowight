package nl.haploid.event.channel

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class AppConfiguration {

  @Bean
  def propertyPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}
