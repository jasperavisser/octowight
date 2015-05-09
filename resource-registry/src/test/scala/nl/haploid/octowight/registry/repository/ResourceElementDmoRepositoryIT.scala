package nl.haploid.octowight.registry.repository

import java.util.Collections

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class ResourceElementDmoRepositoryIT extends AbstractIT {
  @Autowired private[this] val resourceElementDmoRepository: ResourceElementDmoRepository = null

  "Resource element DMO repository" should "find by atom id, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmo = resourceElementDmoRepository.findByAtomIdAndAtomTypeAndAtomOrigin(expectedDmo.getAtomId, expectedDmo.getAtomType, expectedDmo.getAtomOrigin)
    actualDmo should be(expectedDmo)
  }

  "Resource element DMO repository" should "find by atom ids, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmos = resourceElementDmoRepository.findByAtomIdInAndAtomTypeAndAtomOrigin(Collections.singletonList(expectedDmo.getAtomId), expectedDmo.getAtomType, expectedDmo.getAtomOrigin)
    actualDmos should have size 1
    actualDmos.get(0) should be(expectedDmo)
  }

  "Resource element DMO repository" should "delete by resource id & type" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmoBeforeDelete = resourceElementDmoRepository.findOne(expectedDmo.getId)
    actualDmoBeforeDelete should not be null
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(expectedDmo.getResourceType, expectedDmo.getResourceId)
    val actualDmoAfterDelete = resourceElementDmoRepository.findOne(expectedDmo.getId)
    actualDmoAfterDelete should be(null)
  }
}
