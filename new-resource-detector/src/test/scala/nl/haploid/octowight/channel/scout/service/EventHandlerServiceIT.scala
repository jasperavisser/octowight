package nl.haploid.octowight.channel.scout.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.channel.scout.detector.MockResourceDetector
import nl.haploid.octowight.channel.scout.{AbstractIT, TestData}
import nl.haploid.octowight.consumer.service.EventConsumerService
import nl.haploid.octowight.kafka.producer.KafkaOutChannel
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.util.ReflectionTestUtils

class EventHandlerServiceIT extends AbstractIT with KafkaOutChannel {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventHandlerService: EventHandlerService = null
  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  private[this] val topic = TestData.topic

  behavior of "Event handler service"

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(eventConsumerService, "topic", topic)
  }

  it should "handle no events" in {
    val actualCount = eventHandlerService.detectNewResources(10)
    actualCount should be(0)
  }

  it should "handle some events" in {
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
