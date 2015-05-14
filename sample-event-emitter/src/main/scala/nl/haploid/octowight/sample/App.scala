package nl.haploid.octowight.sample

import nl.haploid.octowight.sample.repository.AtomChangeEventDmoRepository
import nl.haploid.octowight.service.EventChannelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.transaction.annotation.Transactional

import scala.collection.JavaConverters._

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@EnableAutoConfiguration
@ComponentScan(basePackages = Array("nl.haploid.octowight"))
class App {
  @Autowired private[this] val eventChannelService: EventChannelService = null
  @Autowired private[this] val atomChangeEventDmoRepository: AtomChangeEventDmoRepository = null

  @Scheduled(fixedRate = 1000)
  @Transactional
  def poll() {
    val eventDmos = atomChangeEventDmoRepository.findAll
    val events = eventDmos.asScala.map(_.toAtomChangeEvent)
    eventChannelService.sendEvents(events)
    atomChangeEventDmoRepository.delete(eventDmos)
  }
}
