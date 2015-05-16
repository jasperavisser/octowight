package nl.haploid.octowight.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class DirtyResourceProducerServiceIT extends AbstractIT {
  @Autowired private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  "Dirty resource producer" should "send dirty resource" in {
    val resourceRoot = TestData.resourceRoot(555l)
    val future = dirtyResourceProducerService.sendDirtyResource(resourceRoot)
    Option(future.get(5, TimeUnit.SECONDS)) should not be None
  }
}
