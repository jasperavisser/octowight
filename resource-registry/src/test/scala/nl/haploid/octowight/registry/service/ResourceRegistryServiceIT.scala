package nl.haploid.octowight.registry.service

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.registry.repository.{ResourceElementDmoRepository, ResourceRootDmoRepository}
import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceRegistryServiceIT extends AbstractIT {
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private[this] val resourceRegistryService: ResourceRegistryService = null
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null

  "Resource registry service" should "return if a resource is new" in {
    val resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo)
    val resourceRoot = ResourceRoot(resourceRootDmo)
    val isNewResource = resourceRegistryService.isNewResource(resourceRoot)
    isNewResource should be(right = false)
  }

  "Resource registry service" should "save a new resource" in {
    val resourceRoot = resourceRegistryService.saveNewResource(TestData.resourceRoot)
    resourceRoot.getResourceId should not be null
    resourceRoot.getVersion should not be null
  }

  "Resource registry service" should "mark resources as dirty" in {
    val resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo)
    val atomGroup = TestData.atomGroup
    val atomChangeEvent1 = TestData.atomChangeEvent(atomGroup)
    val atomChangeEvent2 = TestData.atomChangeEvent(atomGroup)
    val atomChangeEvents = List(atomChangeEvent1, atomChangeEvent2)
    resourceElementDmoRepository.save(TestData.resourceElementDmo(resourceRootDmo, atomChangeEvent1))
    val resourceRoots = resourceRegistryService.markResourcesDirty(atomGroup, atomChangeEvents)
    resourceRoots should have size 1
  }
}
