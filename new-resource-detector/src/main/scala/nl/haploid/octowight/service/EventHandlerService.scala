package nl.haploid.octowight.service

import nl.haploid.octowight.registry.service.ResourceRegistryService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventHandlerService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val resourceDetectorsService: ResourceDetectorsService = null
  @Autowired private[this] val resourceRegistryService: ResourceRegistryService = null
  @Autowired private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  def detectNewResources(batchSize: Int) = {
    log.debug(s"Poll for atom change events on ${eventConsumerService.getTopic}")
    val eventsByGroup = eventConsumerService.consumeDistinctEvents()
      .groupBy(_.atomGroup)
    val count = eventsByGroup
      .flatMap { case (atomGroup, atomChangeEvents) => resourceDetectorsService.detectResources(atomGroup, atomChangeEvents) }
      .flatMap(resourceRegistryService.saveResource)
      .map(dirtyResourceProducerService.sendDirtyResource)
      .map(dirtyResourceProducerService.resolveFuture)
      .size
    log.debug(s"Detected $count new resources")
    eventConsumerService.commit()
    count
  }
}
