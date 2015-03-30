package nl.haploid.event.channel

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled
import org.slf4j.LoggerFactory

object App {
	def main(args : Array[String]) {
		SpringApplication.run(classOf[App])
	}
}

@ComponentScan
@EnableAutoConfiguration
class App {
}
