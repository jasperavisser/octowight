package nl.haploid.octowight.service

import java.util
import java.util.Collections

import nl.haploid.octowight._
import nl.haploid.octowight.detector.ResourceDetector
import org.scalatest.mock.EasyMockSugar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.util.ReflectionTestUtils

@DirtiesContext
class ResourceDetectorsServiceIT extends AbstractIT with EasyMockSugar with EasyMockInjection {
  @Autowired private val service: ResourceDetectorsService = null
  @Mocked private val mockDetector: ResourceDetector = null

  override def beforeEach() = {
    super.beforeEach()
    injectMocks()
    ReflectionTestUtils.setField(service, "detectors", Collections.singletonList(mockDetector))
  }

  "Resource detectors service" should "get detectors for atom type" in {
    val expectedDetectors = Collections.singletonList(mockDetector)
    val atomType = "kinsey"
    expecting {
      mockDetector.getAtomTypes andReturn util.Arrays.asList("crane", atomType) once()
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
    val events = util.Arrays.asList(event1, event2)
    val expectedResourceRoots = Collections.singletonList(TestData.resourceRoot(96l))
    expecting {
      mockDetector.getAtomTypes andReturn util.Arrays.asList("holloway", atomType) once()
      mockDetector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(mockDetector) {
      val actualResourceRoots = service.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
