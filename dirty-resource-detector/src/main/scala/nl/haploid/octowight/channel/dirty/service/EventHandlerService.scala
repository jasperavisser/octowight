package nl.haploid.octowight.channel.dirty.service

import nl.haploid.octowight.consumer.service.{DirtyResourceProducerService, EventConsumerService}
import nl.haploid.octowight.registry.service.ResourceRegistryService
import nl.haploid.octowight.{AtomChangeEvent, AtomGroup}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventHandlerService {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val resourceRegistryService: ResourceRegistryService = null
  @Autowired private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  // TODO: batch size is unused
  // TODO: test
  def detectDirtyResources(batchSize: Int) = {
    log.debug(s"Poll for atom change events on ${eventConsumerService.topic}")
    val eventsByGroup: Map[AtomGroup, Iterable[AtomChangeEvent]] = eventConsumerService.consumeDistinctEvents()
      .groupBy(_.atomGroup)
    val count = eventsByGroup
      .flatMap { case (atomGroup, atomChangeEvents) => resourceRegistryService.markResourcesDirty(atomGroup, atomChangeEvents) }
      .map(dirtyResourceProducerService.sendDirtyResource)
      .map(dirtyResourceProducerService.resolveFuture)
      .size
    log.debug(s"Marked $count resources as dirty")
    eventConsumerService.commit()
    count
  }
}
