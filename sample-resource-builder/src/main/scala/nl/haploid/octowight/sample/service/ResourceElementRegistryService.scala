package nl.haploid.octowight.sample.service

import nl.haploid.octowight.registry.data.{Atom, ResourceIdentifier}
import nl.haploid.octowight.registry.repository.{ResourceElementDmo, ResourceElementDmoRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

// TODO: move to resource-registry
// TODO: IT
@Service
class ResourceElementRegistryService {
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null

  def saveResourceElements(resourceIdentifier: ResourceIdentifier, atoms: Iterable[Atom]): Unit = {
    resourceElementDmoRepository.deleteByResourceCollectionAndResourceId(resourceIdentifier.collection, resourceIdentifier.id)
    val resourceElementDmos = atoms.map(ResourceElementDmo(resourceIdentifier, _)).asJava
    resourceElementDmoRepository.save(resourceElementDmos)
  }
}
