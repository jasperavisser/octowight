package nl.haploid.octowight.emitter.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight._
import nl.haploid.octowight.emitter.{TestData, AbstractTest}
import nl.haploid.octowight.kafka.producer.KafkaProducerFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.EasyMock
import org.springframework.test.util.ReflectionTestUtils

class EventChannelServiceTest extends AbstractTest {
  @Tested private[this] val eventChannelService: EventChannelService = null
  @Mocked private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  behavior of "Event channel service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(eventChannelService, "topic", TestData.nextString)
  }

  it should "send events" in {
    val kafkaProducer = mock[KafkaProducer[String, String]]
    val future1 = mock[Future[RecordMetadata]]
    val future2 = mock[Future[RecordMetadata]]
    val event1 = TestData.atomChangeEvent
    val event2 = TestData.atomChangeEvent
    val events = List(event1, event2)
    expecting {
      kafkaProducerFactory.kafkaProducer andReturn kafkaProducer once()
      val message1 = TestData.nextString
      val message2 = TestData.nextString
      val recordMetadata1 = new RecordMetadata(null, 1, 1)
      val recordMetadata2 = new RecordMetadata(null, 2, 2)
      jsonMapper.serialize(event1) andReturn message1 once()
      kafkaProducer.send(EasyMock.anyObject(classOf[ProducerRecord[String, String]])) andReturn future1 once()
      jsonMapper.serialize(event2) andReturn message2 once()
      kafkaProducer.send(EasyMock.anyObject(classOf[ProducerRecord[String, String]])) andReturn future2 once()
      future1.get(5, TimeUnit.SECONDS) andReturn recordMetadata1 once()
      future2.get(5, TimeUnit.SECONDS) andReturn recordMetadata2 once()
    }
    whenExecuting(jsonMapper, kafkaProducerFactory, kafkaProducer, future1, future2) {
      eventChannelService.sendEvents(events)
    }
  }
}
