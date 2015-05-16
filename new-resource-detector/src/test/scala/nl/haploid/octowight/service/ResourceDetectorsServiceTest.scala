package nl.haploid.octowight.service

import java.util

import nl.haploid.octowight._
import nl.haploid.octowight.detector.ResourceDetector
import org.easymock.EasyMock

class ResourceDetectorsServiceTest extends AbstractTest {
  @Mocked private[this] val detectors: util.List[ResourceDetector] = null

  "Resource detectors service" should "get detectors for atom category" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("getDetectors")
      .createMock()
    val detector1 = mock[ResourceDetector]
    val detector2 = mock[ResourceDetector]
    val expectedDetectors = List(detector1)
    val atomCategory = "sterling"
    expecting {
      resourceDetectorsService.getDetectors andReturn List(detector1, detector2) once()
      detector1.getAtomCategories andReturn List(atomCategory) once()
      detector2.getAtomCategories andReturn List("cooper") once()
    }
    whenExecuting(resourceDetectorsService, detector1, detector2) {
      val actualDetectors = resourceDetectorsService.getDetectorsForAtomCategory(atomCategory)
      actualDetectors should be(expectedDetectors)
    }
  }

  "Resource detectors service" should "detect resources" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("getDetectorsForAtomCategory", classOf[String])
      .createMock()
    val detector = mock[ResourceDetector]
    val event1 = TestData.atomChangeEvent("draper")
    val event2 = TestData.atomChangeEvent("pryce")
    val atomGroup = new AtomGroup(origin = event1.atomOrigin, category = event1.atomCategory)
    val events = List(event1, event2)
    val expectedResourceRoots = List(TestData.resourceRoot(null))
    expecting {
      resourceDetectorsService.getDetectorsForAtomCategory("draper") andReturn List(detector) once()
      detector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(resourceDetectorsService, detector) {
      val actualResourceRoots = resourceDetectorsService.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
