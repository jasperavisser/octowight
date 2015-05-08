package nl.haploid.octowight.service

import nl.haploid.octowight.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class DirtyResourceProducerServiceIT extends AbstractIT {
  @Autowired private val dirtyResourceProducerService: DirtyResourceProducerService = null

  "Dirty resource producer" should "send dirty resource" in {
    val resourceRoot = TestData.resourceRoot(555l)
    val future = dirtyResourceProducerService.sendDirtyResource(resourceRoot)
    future.get should not be null
  }
}
