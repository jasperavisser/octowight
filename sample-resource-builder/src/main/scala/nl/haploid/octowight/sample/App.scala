package nl.haploid.octowight.sample

import nl.haploid.octowight.registry.data.{ResourceIdentifier, ResourceRoot}
import nl.haploid.octowight.registry.repository.ResourceRootDmoRepository
import nl.haploid.octowight.sample.service.{DirtyResourceConsumerService, ResourceBuilderService, ResourceProducerService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.Scheduled

import scala.collection.JavaConverters._

object App {
  def main(args: Array[String]): Unit = SpringApplication.run(classOf[App])
}

@ComponentScan(basePackages = Array("nl.haploid.octowight.sample"))
@EnableAutoConfiguration
class App {
  @Autowired private[this] val dirtyResourceConsumerService: DirtyResourceConsumerService = null
  @Autowired private[this] val resourceBuilderService: ResourceBuilderService = null
  @Autowired private[this] val resourceProducerService: ResourceProducerService = null
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null

  @Scheduled(fixedRate = 1000)
  def poll(): Unit = {
    val resourceIdentifiers = dirtyResourceConsumerService.consumeResourceIdentifiers()
    val resourceIdentifiersByCollection: Map[String, Iterable[ResourceIdentifier]] = resourceIdentifiers.groupBy(id => id.collection)
    val resourceRoots: Iterable[ResourceRoot] = resourceIdentifiersByCollection flatMap {
      case (collection, identifiers) =>
        val ids = identifiers.map(_.id)
        resourceRootDmoRepository.findByResourceCollectionAndResourceIdIn(collection, ids.toList.asJava).asScala
          .map(ResourceRoot(_))
    }
    // TODO: we don't need ResourceRoot, just a tuple class (resourceIdentifier -> atom)
    val resourceRootsByCollection: Map[String, Iterable[ResourceRoot]] = resourceRoots
      .groupBy(_.resourceCollection)
    val resources: Iterable[Resource] = resourceBuilderService.buildResources(resourceRootsByCollection)
    resourceProducerService.send(resources)
    dirtyResourceConsumerService.commit()
  }
}
