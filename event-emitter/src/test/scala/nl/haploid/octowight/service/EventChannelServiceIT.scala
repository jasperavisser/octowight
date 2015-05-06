package nl.haploid.octowight.service

import nl.haploid.octowight.{AbstractIT, TestData}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.springframework.beans.factory.annotation.Autowired

@RunWith(classOf[JUnitRunner])
class EventChannelServiceIT extends AbstractIT {

  @Autowired private val service: EventChannelService = null

  "Channel" should "send an event" in {
    val event = TestData.atomChangeEvent
    val future = service.sendEvent(event)
    future.get should not be null
  }

  "Channel" should "send multiple events" in {
    val results = service.sendEvents(List(TestData.atomChangeEvent, TestData.atomChangeEvent))
    results should have size 2
  }
}
