package nl.haploid.octowight.registry.repository

import java.util.Collections

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.junit.Assert.{assertEquals, assertNotNull, assertNull}
import org.springframework.beans.factory.annotation.Autowired

class ResourceElementDmoRepositoryIT extends AbstractIT {
  @Autowired private val resourceElementDmoRepository: ResourceElementDmoRepository = null

  "Resource element DMO repository" should "find by atom id, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmo = resourceElementDmoRepository.findByAtomIdAndAtomTypeAndAtomOrigin(expectedDmo.getAtomId, expectedDmo.getAtomType, expectedDmo.getAtomOrigin)
    assertEquals(expectedDmo, actualDmo)
  }

  "Resource element DMO repository" should "find by atom ids, type & origin" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmos = resourceElementDmoRepository.findByAtomIdInAndAtomTypeAndAtomOrigin(Collections.singletonList(expectedDmo.getAtomId), expectedDmo.getAtomType, expectedDmo.getAtomOrigin)
    assertEquals(1, actualDmos.size)
    assertEquals(expectedDmo, actualDmos.get(0))
  }

  "Resource element DMO repository" should "delete by resource id & type" in {
    val dmo = TestData.resourceElementDmo
    val expectedDmo = resourceElementDmoRepository.save(dmo)
    val actualDmoBeforeDelete = resourceElementDmoRepository.findOne(expectedDmo.getId)
    assertNotNull(actualDmoBeforeDelete)
    resourceElementDmoRepository.deleteByResourceTypeAndResourceId(expectedDmo.getResourceType, expectedDmo.getResourceId)
    val actualDmoAfterDelete = resourceElementDmoRepository.findOne(expectedDmo.getId)
    assertNull(actualDmoAfterDelete)
  }
}
