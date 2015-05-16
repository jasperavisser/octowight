package nl.haploid.octowight.sample.detector

import java.util

import nl.haploid.octowight.registry.data.ResourceRoot
import nl.haploid.octowight.sample.data.CaptainResource
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmoRepository}
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import nl.haploid.octowight.{Mocked, Tested}
import org.easymock.EasyMock

class CaptainResourceDetectorTest extends AbstractTest {
  @Tested private[this] val detector: CaptainResourceDetector = null
  @Mocked private[this] val roleDmoRepository: RoleDmoRepository = null

  "Captain resource detector" should "have an atom category" in {
    detector.getAtomCategories should have size 1
  }

  "Captain resource detector" should "detect captains" in {
    val detector = withMocks(EasyMock.createMockBuilder(classOf[CaptainResourceDetector])
      .addMockedMethod("getRolesById")
      .addMockedMethod("isCaptain")
      .createMock())
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val event3 = TestData.atomChangeEvent
    val events = List(event1, event2, event3)
    val roleDmo1 = TestData.roleDmo(event1.getAtomId)
    val roleDmo2 = TestData.roleDmo(event2.getAtomId)
    val rolesById = Map((event1.getAtomId, roleDmo1), (event2.getAtomId, roleDmo2))
    val resourceRoot = ResourceRoot(event2, CaptainResource.ResourceType)
    val expectedResourceRoots = List(resourceRoot)
    expecting {
      detector.getRolesById(events) andReturn rolesById once()
      detector.isCaptain(roleDmo1) andReturn false once()
      detector.isCaptain(roleDmo2) andReturn true once()
    }
    whenExecuting(detector) {
      val actualResourceRoots = detector.detect(events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }

  "Captain resource detector" should "get roles by id" in {
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val events = List(event1, event2)
    val id1 = event1.getAtomId
    val id2 = event2.getAtomId
    val roleDmo1 = TestData.roleDmo(id1)
    val roleDmo2 = TestData.roleDmo(id2)
    expecting {
      roleDmoRepository.findAll(util.Arrays.asList(id1, id2)) andReturn util.Arrays.asList(roleDmo1, roleDmo2) once()
    }
    whenExecuting(roleDmoRepository) {
      val rolesById = detector.getRolesById(events)
      rolesById.get(id1).orNull should be(roleDmo1)
      rolesById.get(id2).orNull should be(roleDmo2)
    }
  }

  "Captain resource detector" should "recognize a captain" in {
    val personDmo = mock[PersonDmo]
    val roleDmo1 = TestData.roleDmo(personDmo, "harpooner")
    val roleDmo2 = TestData.roleDmo(personDmo, CaptainResourceDetector.RoleType)
    detector.isCaptain(roleDmo1) should be(right = false)
    detector.isCaptain(roleDmo2) should be(right = true)
  }
}
