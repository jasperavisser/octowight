package nl.haploid.jooq

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

object App {
	def main(args : Array[String]) {
		SpringApplication.run(classOf[App])
	}
}

@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
class App {
}
