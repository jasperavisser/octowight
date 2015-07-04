package nl.haploid.octowight.service

import nl.haploid.octowight._
import nl.haploid.octowight.detector.ResourceDetector
import org.easymock.EasyMock

class ResourceDetectorsServiceTest extends AbstractTest {

  behavior of "Resource detectors service"

  it should "get detectors for atom category" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("detectors")
      .createMock()
    val detector1 = mock[ResourceDetector]
    val detector2 = mock[ResourceDetector]
    val expectedDetectors = List(detector1)
    val atomCategory = "sterling"
    expecting {
      resourceDetectorsService.detectors andReturn List(detector1, detector2) once()
      detector1.atomCategories andReturn List(atomCategory) once()
      detector2.atomCategories andReturn List("cooper") once()
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
    val atomGroup = new AtomGroup(origin = event1.atomOrigin, category = event1.atomCategory)
    val events = List(event1, event2)
    val expectedResourceRoots = List(TestData.resourceRoot(null))
    expecting {
      resourceDetectorsService.detectorsForAtomCategory("draper") andReturn List(detector) once()
      detector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(resourceDetectorsService, detector) {
      val actualResourceRoots = resourceDetectorsService.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
