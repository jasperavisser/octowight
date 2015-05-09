package nl.haploid.octowight.service

import nl.haploid.octowight.registry.service.ResourceRegistryService
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._
import scala.collection.mutable

@Service
class EventHandlerService {
  private val log = LoggerFactory.getLogger(getClass)

  @Autowired private val eventConsumerService: EventConsumerService = null
  @Autowired private val resourceDetectorsService: ResourceDetectorsService = null
  @Autowired private val resourceRegistryService: ResourceRegistryService = null
  @Autowired private val dirtyResourceProducerService: DirtyResourceProducerService = null

  def detectNewResources(batchSize: Int) = {
    log.debug(s"Poll for atom change events on ${eventConsumerService.getTopic}")
    val events: Map[AtomGroup, mutable.Buffer[AtomChangeEvent]] = eventConsumerService.consumeMessages()
      .asScala
      .groupBy(_.getAtomGroup)
    log.debug(s"Consumed ${events.size} events")
    val count = events
      .flatMap { case (atomGroup, atomChangeEvents) => resourceDetectorsService.detectResources(atomGroup, atomChangeEvents.asJava).asScala }
      .filter(resourceRegistryService.isNewResource)
      .map(resourceRegistryService.saveNewResource)
      .map(dirtyResourceProducerService.sendDirtyResource)
      .map(dirtyResourceProducerService.resolveFuture)
      .size
    log.debug(s"Detected $count new resources")
    eventConsumerService.commit()
    count
  }
}