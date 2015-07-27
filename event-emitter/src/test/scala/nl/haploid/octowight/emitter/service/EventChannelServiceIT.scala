package nl.haploid.octowight.emitter.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.emitter.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class EventChannelServiceIT extends AbstractIT {
  @Autowired private[this] val eventChannelService: EventChannelService = null

  behavior of "Event channel service"

  it should "send an event" in {
    val event = TestData.atomChangeEvent
    val future = eventChannelService.sendEvent(event)
    Option(future.get(5, TimeUnit.SECONDS)) should not be None
  }

  it should "send multiple events" in {
    val results = eventChannelService.sendEvents(List(TestData.atomChangeEvent, TestData.atomChangeEvent))
    results should have size 2
  }
}
