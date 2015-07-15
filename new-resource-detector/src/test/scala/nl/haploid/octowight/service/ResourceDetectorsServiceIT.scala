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
  @Autowired private[this] val resourceDetectorsService: ResourceDetectorsService = null
  @Mocked private[this] val mockDetector: ResourceDetector = null

  behavior of "Resource detectors service"

  override def beforeEach() = {
    super.beforeEach()
    injectMocks()
    ReflectionTestUtils.setField(resourceDetectorsService, "_detectors", util.Collections.singletonList(mockDetector))
  }

  it should "get detectors for atom category" in {
    val expectedDetectors = List(mockDetector)
    val atomCategory = "kinsey"
    expecting {
      mockDetector.atomCategories andReturn List("crane", atomCategory) once()
    }
    whenExecuting(mockDetector) {
      val actualDetectors = resourceDetectorsService.detectorsForAtomCategory(atomCategory)
      actualDetectors should be(expectedDetectors)
    }
  }

  it should "detect resources" in {
    val atomCategory = "harris"
    val event1 = newresourcedetector.TestData.atomChangeEvent(atomCategory)
    val event2 = newresourcedetector.TestData.atomChangeEvent("calvet")
    val atomGroup = new AtomGroup(origin = event1.atomOrigin, category = event1.atomCategory)
    val events = List(event1, event2)
    val expectedResourceRoots = List(newresourcedetector.TestData.resourceRoot(newresourcedetector.TestData.nextLong))
    expecting {
      mockDetector.atomCategories andReturn List("holloway", atomCategory) once()
      mockDetector.detect(events) andReturn expectedResourceRoots once()
    }
    whenExecuting(mockDetector) {
      val actualResourceRoots = resourceDetectorsService.detectResources(atomGroup, events)
      actualResourceRoots should be(expectedResourceRoots)
    }
  }
}
