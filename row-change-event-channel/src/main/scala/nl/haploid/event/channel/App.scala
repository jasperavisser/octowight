package nl.haploid.event.channel

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

object App {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[App])
  }
}

@ComponentScan
@EnableAutoConfiguration
class App {
}
