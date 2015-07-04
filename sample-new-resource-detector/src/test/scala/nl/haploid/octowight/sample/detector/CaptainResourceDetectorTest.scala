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

  behavior of "Captain resource detector"

  it should "have an atom category" in {
    detector.atomCategories should have size 1
  }

  it should "detect captains" in {
    val detector = withMocks(EasyMock.createMockBuilder(classOf[CaptainResourceDetector])
      .addMockedMethod("findRolesById")
      .addMockedMethod("isCaptain")
      .createMock())
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val event3 = TestData.atomChangeEvent
    val events = List(event1, event2, event3)
    val roleDmo1 = TestData.roleDmo(event1.atomId)
    val roleDmo2 = TestData.roleDmo(event2.atomId)
    val rolesById = Map((event1.atomId, roleDmo1), (event2.atomId, roleDmo2))
    val resourceRoot = ResourceRoot(event2, CaptainResource.ResourceCollection)
    val expectedResourceRoots = List(resourceRoot)
    expecting {
      detector.findRolesById(events) andReturn rolesById once()
      detector.isCaptain(roleDmo1) andReturn false once()
      detector.isCaptain(roleDmo2) andReturn true once()
    }
    whenExecuting(detector) {
      val actualResourceRoots = detector.detect(events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }

  it should "get roles by id" in {
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val events = List(event1, event2)
    val id1 = event1.atomId
    val id2 = event2.atomId
    val roleDmo1 = TestData.roleDmo(id1)
    val roleDmo2 = TestData.roleDmo(id2)
    expecting {
      roleDmoRepository.findAll(util.Arrays.asList(id1, id2)) andReturn util.Arrays.asList(roleDmo1, roleDmo2) once()
    }
    whenExecuting(roleDmoRepository) {
      val rolesById = detector.findRolesById(events)
      rolesById.get(id1).orNull should be(roleDmo1)
      rolesById.get(id2).orNull should be(roleDmo2)
    }
  }

  it should "recognize a captain" in {
    val personDmo = mock[PersonDmo]
    val roleDmo1 = TestData.roleDmo(personDmo, "harpooner")
    val roleDmo2 = TestData.roleDmo(personDmo, CaptainResourceDetector.RoleType)
    detector.isCaptain(roleDmo1) should be(right = false)
    detector.isCaptain(roleDmo2) should be(right = true)
  }
}
