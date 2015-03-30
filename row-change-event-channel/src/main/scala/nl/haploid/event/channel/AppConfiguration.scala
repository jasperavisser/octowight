package nl.haploid.event.channel

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
//@ComponentScan(basePackages = Array("nl.haploid.event.channel"))
@EnableScheduling
class AppConfiguration {

	@Bean
	def propertyPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
}
