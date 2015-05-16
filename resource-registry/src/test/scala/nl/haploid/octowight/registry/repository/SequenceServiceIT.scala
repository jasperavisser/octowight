package nl.haploid.octowight.registry.repository

import nl.haploid.octowight.registry.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class SequenceServiceIT extends AbstractIT {
  @Autowired private[this] val sequenceService: SequenceService = null

  "Sequence service" should "get the next value from a sequence" in {
    val key = s"test-${TestData.nextString}"
    sequenceService.nextValue(key) should be(0)
    sequenceService.nextValue(key) should be(1)
    sequenceService.nextValue(key) should be(2)
    sequenceService.nextValue(key) should be(3)
  }
}
