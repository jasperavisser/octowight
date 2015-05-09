package nl.haploid.octowight.sample

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@ComponentScan(basePackages = Array("nl.haploid.octowight"))
@EnableAutoConfiguration
class App
