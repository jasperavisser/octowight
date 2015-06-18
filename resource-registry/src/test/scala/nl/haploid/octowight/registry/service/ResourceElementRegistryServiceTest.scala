package nl.haploid.octowight.registry.service

import nl.haploid.octowight.{Tested, Mocked}
import nl.haploid.octowight.registry.{AbstractTest, TestData}
import nl.haploid.octowight.registry.repository.{ResourceElementDmo, ResourceElementDmoRepository}

import scala.collection.JavaConverters._

class ResourceElementRegistryServiceTest extends AbstractTest {
  @Tested private[this] val resourceElementRegistryService: ResourceElementRegistryService = null
  @Mocked private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null

  it should "save resource elements" in {
    val resourceIdentifier = TestData.resourceIdentifier
    val atom1 = TestData.atom
    val atom2 = TestData.atom
    val resourceElementDmo1 = ResourceElementDmo(resourceIdentifier, atom1)
    val resourceElementDmo2 = ResourceElementDmo(resourceIdentifier, atom2)
    val resourceElementDmos = Iterable(resourceElementDmo1, resourceElementDmo2)
    expecting {
      resourceElementDmoRepository.deleteByResourceCollectionAndResourceId(resourceIdentifier.collection, resourceIdentifier.id) andReturn 0 once()
      resourceElementDmoRepository.save(resourceElementDmos.asJava) andReturn resourceElementDmos.toList.asJava once()
    }
    whenExecuting(resourceElementDmoRepository) {
      resourceElementRegistryService.saveResourceElements(resourceIdentifier, List(atom1, atom2))
    }
  }
}
