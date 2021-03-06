package nl.haploid.octowight.registry.repository

import java.util

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceRootDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val resourceRootDmoRepository: ResourceRootDmoRepository = null

  behavior of "Resource root repository"

  it should "find by atom id, type & origin" in {
    resourceRootDmoRepository.deleteAll()
    val dmo = TestData.resourceRootDmo
    val expectedDmo = resourceRootDmoRepository.save(dmo)
    val actualDmo = resourceRootDmoRepository.findByResourceCollectionAndRootIdAndRootCategoryAndRootOrigin(
      expectedDmo.resourceCollection, expectedDmo.root.id, expectedDmo.root.category, expectedDmo.root.origin)
    actualDmo should be(expectedDmo)
    println(actualDmo)
  }

  it should "find by resource collection" in {
    resourceRootDmoRepository.deleteAll()
    val dmo1 = TestData.resourceRootDmo("willow")
    val dmo2 = TestData.resourceRootDmo("oz")
    val expectedDmo1 = resourceRootDmoRepository.save(dmo1)
    resourceRootDmoRepository.save(dmo2)
    val actualDmos = resourceRootDmoRepository.findByResourceCollection("willow")
    val expectedDmos = util.Collections.singletonList(expectedDmo1)
    actualDmos should be(expectedDmos)
  }

  it should "find by resource collection & id" in {
    resourceRootDmoRepository.deleteAll()
    val dmo1 = TestData.resourceRootDmo("willow")
    val dmo2 = TestData.resourceRootDmo("oz")
    val expectedDmo = resourceRootDmoRepository.save(dmo1)
    resourceRootDmoRepository.save(dmo2)
    val actualDmo = resourceRootDmoRepository.findByResourceCollectionAndResourceId("willow", expectedDmo.resourceId)
    actualDmo should be(expectedDmo)
  }
}
