package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.data.{CaptainModel, JsonModelSerializer}
import nl.haploid.octowight.sample.service.{CaptainCacheService, ResourceConsumerService}
import org.springframework.beans.factory.annotation.Autowired
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

  @Autowired
  private[this] val resourceConsumerService: ResourceConsumerService = null

  @Autowired
  private[this] val cacheService: CaptainCacheService = null

  @Autowired
  private[this] val serializer: JsonModelSerializer[CaptainModel] = null

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = {

    val resourceMessages = resourceConsumerService.consumeResourceMessages().toList
    resourceMessages.foreach(message => cacheService.saveResource(message))
    resourceConsumerService.commit()
  }

}
