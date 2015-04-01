package nl.haploid.event.channel.service

import java.util

import nl.haploid.event.channel.EventChannelService
import nl.haploid.event.channel.repository.{RowChangeEvent, RowChangeEventRepository}
import org.scalatest.mock.EasyMockSugar
import org.scalatest.{FlatSpec, Matchers}

class EventChannelServiceTest extends FlatSpec with EasyMockSugar with Matchers {

  "Event channel service" should "transfer events from repository into topic" in {
    val mockRepository = mock[RowChangeEventRepository]
    val service = new EventChannelService(mockRepository)
    val expectedEvents = new util.ArrayList[RowChangeEvent]
    implicit val mocks = MockObjects(mockRepository)
    expecting {
      mockRepository.findAll andReturn expectedEvents
      mockRepository.delete(expectedEvents)
    }
    whenExecuting {
      service.queueRowChangeEvents
    }
  }
}