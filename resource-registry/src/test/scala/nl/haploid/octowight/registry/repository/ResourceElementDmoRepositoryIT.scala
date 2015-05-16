package nl.haploid.octowight.registry.repository

import java.util

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceElementDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null

  "Resource element DMO repository" should "find by atom id, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmo = resourceElementDmoRepository.findByAtomIdAndAtomCategoryAndAtomOrigin(
      expectedDmo.atom.id, expectedDmo.atom.category, expectedDmo.atom.origin)
    actualDmo should be(expectedDmo)
  }

  "Resource element DMO repository" should "find by atom ids, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmos = resourceElementDmoRepository.findByAtomIdInAndAtomCategoryAndAtomOrigin(
      util.Collections.singletonList(expectedDmo.atom.id), expectedDmo.atom.category, expectedDmo.atom.origin)
    actualDmos should have size 1
    actualDmos.get(0) should be(expectedDmo)
  }

  "Resource element DMO repository" should "delete by resource id & type" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmoBeforeDelete = resourceElementDmoRepository.findOne(expectedDmo.id)
    actualDmoBeforeDelete should not be null
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(expectedDmo.resourceType, expectedDmo.resourceId)
    val actualDmoAfterDelete = resourceElementDmoRepository.findOne(expectedDmo.id)
    actualDmoAfterDelete should be(null)
  }
}
