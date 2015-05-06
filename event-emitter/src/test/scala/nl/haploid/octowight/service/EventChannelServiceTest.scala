package nl.haploid.octowight.service

import java.util.concurrent.{TimeUnit, Future}

import nl.haploid.octowight._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.EasyMock
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.EasyMockSugar
import org.springframework.test.util.ReflectionTestUtils

@RunWith(classOf[JUnitRunner])
class EventChannelServiceTest extends FlatSpec with EasyMockSugar
with ShouldMatchers with EasyMockInjection with BeforeAndAfterEach {

  @Tested private val eventChannelService = new EventChannelService
  @Mocked private val kafkaProducer: KafkaProducer[String, String] = null
  @Mocked private val jsonMapper: JsonMapper = null

  override def beforeEach() = {
    injectMocks()
    ReflectionTestUtils.setField(eventChannelService, "topic", TestData.topic)
  }

  "Channel" should "send events" in {
    val future1 = mock[Future[RecordMetadata]]
    val future2 = mock[Future[RecordMetadata]]
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val events = List(event1, event2)

    expecting {
      val message1 = TestData.message
      val message2 = TestData.message
      val recordMetadata1 = new RecordMetadata(null, 1, 1)
      val recordMetadata2 = new RecordMetadata(null, 2, 2)
      jsonMapper.serialize(event1) andReturn message1 once()
      kafkaProducer.send(EasyMock.anyObject(classOf[ProducerRecord[String, String]])) andReturn future1 once()
      jsonMapper.serialize(event2) andReturn message2 once()
      kafkaProducer.send(EasyMock.anyObject(classOf[ProducerRecord[String, String]])) andReturn future2 once()
      future1.get(5, TimeUnit.SECONDS) andReturn recordMetadata1 once()
      future2.get(5, TimeUnit.SECONDS) andReturn recordMetadata2 once()
    }
    whenExecuting(jsonMapper, kafkaProducer, future1, future2) {
      eventChannelService.sendEvents(events)
    }
  }
}
