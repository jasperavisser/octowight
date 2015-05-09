package nl.haploid.octowight.sample

import nl.haploid.octowight.service.EventHandlerService
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@ComponentScan(basePackages = Array("nl.haploid.octowight"))
@EnableAutoConfiguration
class App {
  @Value("${octowight.kafka.batch.size}") private val batchSize: Int = 0
  @Autowired private val eventHandlerService: EventHandlerService = null

  @Scheduled(fixedRate = 1000)
  def poll() = eventHandlerService.detectNewResources(batchSize)
}
