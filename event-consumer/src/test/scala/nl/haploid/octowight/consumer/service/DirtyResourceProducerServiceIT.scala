package nl.haploid.octowight.consumer.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.consumer.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class DirtyResourceProducerServiceIT extends AbstractIT {
  @Autowired private[this] val dirtyResourceProducerService: DirtyResourceProducerService = null

  behavior of "Dirty resource producer service"

  it should "send a dirty resource" in {
    val resourceRoot = TestData.resourceRoot(TestData.nextLong)
    val future = dirtyResourceProducerService.sendDirtyResource(resourceRoot)
    Option(future.get(5, TimeUnit.SECONDS)) should not be None
  }
}
