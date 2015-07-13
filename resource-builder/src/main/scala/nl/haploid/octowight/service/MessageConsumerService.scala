package nl.haploid.octowight.service

import nl.haploid.octowight.registry.data.{ResourceIdentifier, ResourceMessage, ResourceRoot}
import nl.haploid.octowight.registry.service.ResourceRegistryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageConsumerService {
  @Autowired private[this] val dirtyResourceConsumerService: DirtyResourceConsumerService = null
  @Autowired private[this] val resourceBuilderService: ResourceBuilderService = null
  @Autowired private[this] val resourceProducerService: ResourceProducerService = null
  @Autowired private[this] val resourceRegistryService: ResourceRegistryService = null

  def buildAndProduceResources(): Unit = {
    val resourceIdentifiersByCollection: Map[String, Iterable[ResourceIdentifier]] = dirtyResourceConsumerService.consumeResourceIdentifiers()
    val resourceRootsByCollection: Map[String, Iterable[ResourceRoot]] = resourceRegistryService.findResourceRoots(resourceIdentifiersByCollection)
    val resources: Iterable[ResourceMessage] = resourceBuilderService.buildResources(resourceRootsByCollection)
    resourceProducerService.send(resources)
    dirtyResourceConsumerService.commit()
  }
}
