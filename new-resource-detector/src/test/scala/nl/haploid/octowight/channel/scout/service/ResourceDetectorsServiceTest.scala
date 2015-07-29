package nl.haploid.octowight.channel.scout.service

import nl.haploid.octowight.AtomGroup
import nl.haploid.octowight.channel.scout.detector.ResourceDetector
import nl.haploid.octowight.channel.scout.{AbstractTest, TestData}
import org.easymock.EasyMock

class ResourceDetectorsServiceTest extends AbstractTest {

  behavior of "Resource detectors service"

  it should "get detectors for atom category" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("detectors")
      .createMock()
    val detector1 = mock[ResourceDetector]
    val detector2 = mock[ResourceDetector]
    val expectedDetectors = Set(detector1)
    val atomCategory = "sterling"
    expecting {
      resourceDetectorsService.detectors andReturn Set(detector1, detector2) once()
      detector1.atomCategories andReturn Set(atomCategory) once()
      detector2.atomCategories andReturn Set("cooper") once()
    }
    whenExecuting(resourceDetectorsService, detector1, detector2) {
      val actualDetectors = resourceDetectorsService.detectorsForAtomCategory(atomCategory)
      actualDetectors should be(expectedDetectors)
    }
  }

  it should "detect resources" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("detectorsForAtomCategory", classOf[String])
      .createMock()
    val detector = mock[ResourceDetector]
    val event1 = TestData.atomChangeEvent("draper")
    val event2 = TestData.atomChangeEvent("pryce")
    val atomGroup = new AtomGroup(origin = event1.origin, category = event1.category)
    val atomIds: Set[Long] = Set(event1.id, event2.id)
    val expectedResourceRoots = Set(TestData.resourceRoot(123L))
    expecting {
      resourceDetectorsService.detectorsForAtomCategory("draper") andReturn Set(detector) once()
      detector.detect(atomGroup, atomIds) andReturn expectedResourceRoots once()
    }
    whenExecuting(resourceDetectorsService, detector) {
      val actualResourceRoots = resourceDetectorsService.detectResources(atomGroup, atomIds)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
