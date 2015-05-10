package nl.haploid.octowight.registry.repository

import java.util

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceRootDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null

  "Resource root DMO repository" should "find by atom id, type & origin" in {
    resourceRootDmoRepository.deleteAll()
    val dmo = TestData.resourceRootDmo
    val expectedDmo = resourceRootDmoRepository.save(dmo)
    val actualDmo = resourceRootDmoRepository.findByResourceTypeAndAtomIdAndAtomTypeAndAtomOrigin(expectedDmo.getResourceType, expectedDmo.getAtomId, expectedDmo.getAtomType, expectedDmo.getAtomOrigin)
    actualDmo should be(expectedDmo)
  }

  "Resource root DMO repository" should "find by resource type" in {
    resourceRootDmoRepository.deleteAll()
    val dmo1 = TestData.resourceRootDmo("willow")
    val dmo2 = TestData.resourceRootDmo("oz")
    val expectedDmo1 = resourceRootDmoRepository.save(dmo1)
    resourceRootDmoRepository.save(dmo2)
    val actualDmos = resourceRootDmoRepository.findByResourceType("willow")
    val expectedDmos = util.Collections.singletonList(expectedDmo1)
    actualDmos should be(expectedDmos)
  }

  "Resource root DMO repository" should "find by resource type & id" in {
    resourceRootDmoRepository.deleteAll()
    val dmo1 = TestData.resourceRootDmo("willow")
    val dmo2 = TestData.resourceRootDmo("oz")
    val expectedDmo = resourceRootDmoRepository.save(dmo1)
    resourceRootDmoRepository.save(dmo2)
    val actualDmo = resourceRootDmoRepository.findByResourceTypeAndResourceId("willow", expectedDmo.getResourceId)
    actualDmo should be(expectedDmo)
  }
}
