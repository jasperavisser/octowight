package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class SequenceServiceIT extends AbstractIT {
  @Autowired private val sequenceService: SequenceService = null

  "Sequence service" should "get the next value from a sequence" in {
    val key = s"test-${TestData.nextString}"
    sequenceService.getNextValue(key) should be(0)
    sequenceService.getNextValue(key) should be(1)
    sequenceService.getNextValue(key) should be(2)
    sequenceService.getNextValue(key) should be(3)
  }
}
