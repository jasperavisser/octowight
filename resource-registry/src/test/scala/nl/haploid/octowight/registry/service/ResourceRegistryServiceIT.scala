package nl.haploid.octowight.registry.service

import java.util

import nl.haploid.octowight.registry.data.ResourceRootFactory
import nl.haploid.octowight.registry.repository.{ResourceElementDmoRepository, ResourceRootDmoRepository}
import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.junit.Assert.{assertEquals, assertFalse, assertNotNull}
import org.springframework.beans.factory.annotation.Autowired

class ResourceRegistryServiceIT extends AbstractIT {
  @Autowired private val resourceElementDmoRepository: ResourceElementDmoRepository = null
  @Autowired private val resourceRegistryService: ResourceRegistryService = null
  @Autowired private val resourceRootDmoRepository: ResourceRootDmoRepository = null
  @Autowired private val resourceRootFactory: ResourceRootFactory = null

  "Resource registry service" should "return if a resource is new" in {
    val resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo)
    val resourceRoot = resourceRootFactory.fromResourceRootDmo(resourceRootDmo)
    val isNewResource = resourceRegistryService.isNewResource(resourceRoot)
    assertFalse(isNewResource)
  }

  "Resource registry service" should "save a new resource" in {
    val resourceRoot = resourceRegistryService.saveNewResource(TestData.resourceRoot)
    assertNotNull(resourceRoot.getResourceId)
    assertNotNull(resourceRoot.getVersion)
  }

  "Resource registry service" should "mark resources as dirty" in {
    val resourceRootDmo = resourceRootDmoRepository.save(TestData.resourceRootDmo)
    val atomGroup = TestData.atomGroup
    val atomChangeEvent1 = TestData.atomChangeEvent(atomGroup)
    val atomChangeEvent2 = TestData.atomChangeEvent(atomGroup)
    val atomChangeEvents = util.Arrays.asList(atomChangeEvent1, atomChangeEvent2)
    resourceElementDmoRepository.save(TestData.resourceElementDmo(resourceRootDmo, atomChangeEvent1))
    val resourceRoots = resourceRegistryService.markResourcesDirty(atomGroup, atomChangeEvents)
    assertEquals(1, resourceRoots.size)
  }
}
