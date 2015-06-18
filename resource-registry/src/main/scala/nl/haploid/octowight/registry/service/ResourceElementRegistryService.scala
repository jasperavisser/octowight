package nl.haploid.octowight.registry.service

import nl.haploid.octowight.registry.data.{Atom, ResourceIdentifier}
import nl.haploid.octowight.registry.repository.{ResourceElementDmo, ResourceElementDmoRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class ResourceElementRegistryService {
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null

  def saveResourceElements(resourceIdentifier: ResourceIdentifier, atoms: Iterable[Atom]): Unit = {
    resourceElementDmoRepository.deleteByResourceCollectionAndResourceId(resourceIdentifier.collection, resourceIdentifier.id)
    val resourceElementDmos = atoms.map(ResourceElementDmo(resourceIdentifier, _)).asJava
    resourceElementDmoRepository.save(resourceElementDmos)
  }
}
