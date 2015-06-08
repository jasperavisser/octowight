package nl.haploid.octowight.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.detector.MockResourceDetector
import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.{AbstractIT, AtomChangeEvent, JsonMapper, TestData}
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.util.ReflectionTestUtils

class EventHandlerServiceIT extends AbstractIT {
  @Autowired private[this] val eventHandlerService: EventHandlerService = null
  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  private[this] lazy val kafkaProducer = kafkaProducerFactory.kafkaProducer

  private[this] val topic = TestData.topic

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(eventConsumerService, "topic", topic)
  }

  "Event handler service" should "handle no events" in {
    val actualCount = eventHandlerService.detectNewResources(10)
    actualCount should be(0)
  }

  "Event handler service" should "handle some events" in {
    val event1 = TestData.atomChangeEvent(MockResourceDetector.AtomCategory)
    val event2 = TestData.atomChangeEvent(MockResourceDetector.AtomCategory)
    val event3 = TestData.atomChangeEvent("jack")
    sendMessage(topic, event1)
    sendMessage(topic, event2)
    sendMessage(topic, event3)
    val actualCount = eventHandlerService.detectNewResources(10)
    actualCount should be(2)
  }

  def sendMessage(topic: String, event: AtomChangeEvent) = {
    val message = jsonMapper.serialize(event)
    log.debug(s"Send message to $topic: $message")
    val record = new ProducerRecord[String, String](topic, message)
    kafkaProducer.send(record).get(5, TimeUnit.SECONDS)
  }
}
