package nl.haploid.octowight

import nl.haploid.octowight.service.MessageConsumerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled

// TODO: library should not have a main function
object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@ComponentScan(basePackages = Array("nl.haploid.octowight"))
@EnableAutoConfiguration
class App {
  @Autowired private[this] val messageConsumerService: MessageConsumerService = null

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = messageConsumerService.buildAndProduceResources()
}
