package nl.haploid.octowight.service

import java.util

import nl.haploid.octowight._
import nl.haploid.octowight.detector.ResourceDetector
import org.scalatest.mock.EasyMockSugar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.util.ReflectionTestUtils

@DirtiesContext
class ResourceDetectorsServiceIT extends AbstractIT with EasyMockSugar with EasyMockInjection {
  @Autowired private[this] val service: ResourceDetectorsService = null
  @Mocked private[this] val mockDetector: ResourceDetector = null

  override def beforeEach() = {
    super.beforeEach()
    injectMocks()
    ReflectionTestUtils.setField(service, "detectors", util.Collections.singletonList(mockDetector))
  }

  "Resource detectors service" should "get detectors for atom type" in {
    val expectedDetectors = List(mockDetector)
    val atomType = "kinsey"
    expecting {
      mockDetector.getAtomTypes andReturn List("crane", atomType) once()
    }
    whenExecuting(mockDetector) {
      val actualDetectors = service.getDetectorsForAtomType(atomType)
      actualDetectors should be(expectedDetectors)
    }
  }

  "Resource detectors service" should "detect resources" in {
    val atomType = "harris"
    val event1 = TestData.atomChangeEvent(atomType)
    val event2 = TestData.atomChangeEvent("calvet")
    val atomGroup = new AtomGroup
    atomGroup.setAtomOrigin(event1.getAtomOrigin)
    atomGroup.setAtomType(event1.getAtomType)
    val events = List(event1, event2)
    val expectedResourceRoots = List(TestData.resourceRoot(96l))
    expecting {
      mockDetector.getAtomTypes andReturn List("holloway", atomType) once()
      mockDetector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(mockDetector) {
      val actualResourceRoots = service.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
