package nl.haploid.octowight.service

import nl.haploid.octowight.registry.service.ResourceRegistryService
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventHandlerService {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val resourceDetectorsService: ResourceDetectorsService = null
  @Autowired private[this] val resourceRegistryService: ResourceRegistryService = null
  @Autowired private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  def detectNewResources(batchSize: Int) = {
    log.debug(s"Poll for atom change events on ${eventConsumerService.getTopic}")
    val events: Map[AtomGroup, Iterable[AtomChangeEvent]] = eventConsumerService.consumeMessages()
      .groupBy(_.getAtomGroup)
    log.debug(s"Consumed ${events.size} events")
    val count = events
      .flatMap { case (atomGroup, atomChangeEvents) => resourceDetectorsService.detectResources(atomGroup, atomChangeEvents) }
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
