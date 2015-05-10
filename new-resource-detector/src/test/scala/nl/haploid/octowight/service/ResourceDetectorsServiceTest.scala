package nl.haploid.octowight.service

import java.util

import nl.haploid.octowight._
import nl.haploid.octowight.detector.ResourceDetector
import org.easymock.EasyMock

import scala.collection.mutable

class ResourceDetectorsServiceTest extends AbstractTest {
  @Mocked private[this] val detectors: util.List[ResourceDetector] = null

  override def beforeEach() = {
    super.beforeEach()
  }

  "Resource detectors service" should "get detectors for atom type" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("getDetectors")
      .createMock()
    val detector1 = mock[ResourceDetector]
    val detector2 = mock[ResourceDetector]
    val expectedDetectors = List(detector1)
    val atomType = "sterling"
    expecting {
      resourceDetectorsService.getDetectors andReturn mutable.Buffer(detector1, detector2) once()
      detector1.getAtomTypes andReturn List(atomType) once()
      detector2.getAtomTypes andReturn List("cooper") once()
    }
    whenExecuting(resourceDetectorsService, detector1, detector2) {
      val actualDetectors = resourceDetectorsService.getDetectorsForAtomType(atomType)
      actualDetectors should be(expectedDetectors)
    }
  }

  "Resource detectors service" should "detect resources" in {
    val resourceDetectorsService = EasyMock.createMockBuilder(classOf[ResourceDetectorsService])
      .addMockedMethod("getDetectorsForAtomType", classOf[String])
      .createMock()
    val detector = mock[ResourceDetector]
    val event1 = TestData.atomChangeEvent("draper")
    val event2 = TestData.atomChangeEvent("pryce")
    val atomGroup = new AtomGroup
    atomGroup.setAtomOrigin(event1.getAtomOrigin)
    atomGroup.setAtomType(event1.getAtomType)
    val events = List(event1, event2)
    val expectedResourceRoots = List(TestData.resourceRoot(null))
    expecting {
      resourceDetectorsService.getDetectorsForAtomType("draper") andReturn mutable.Buffer(detector) once()
      detector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(resourceDetectorsService, detector) {
      val actualResourceRoots = resourceDetectorsService.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
