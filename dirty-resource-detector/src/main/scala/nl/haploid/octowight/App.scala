package nl.haploid.octowight

import nl.haploid.octowight.service.EventHandlerService
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@EnableAutoConfiguration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class App {
  @Autowired private[this] val eventHandlerService: EventHandlerService = null
  @Value("${octowight.kafka.batch.size}") private[this] val batchSize: Int = 0

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = eventHandlerService.detectDirtyResources(batchSize)
}
