package nl.haploid.octowight.service

import nl.haploid.octowight.detector.MockResourceDetector
import nl.haploid.octowight.{AbstractIT, AtomChangeEvent, JsonMapper, TestData}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.springframework.beans.factory.annotation.Autowired

class EventHandlerServiceIT extends AbstractIT {
  @Autowired private[this] val eventHandlerService: EventHandlerService = null
  @Autowired private[this] val eventConsumerService: EventConsumerService = null
  @Autowired private[this] val kafkaProducer: KafkaProducer[String, String] = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  "Event handler service" should "handle no events" in {
    val actualCount = eventHandlerService.detectNewResources(10)
    actualCount should be(0)
  }

  "Event handler service" should "handle some events" in {
    val topic = TestData.topic
    eventConsumerService.setTopic(topic)
    val event1 = TestData.atomChangeEvent(MockResourceDetector.AtomType)
    val event2 = TestData.atomChangeEvent(MockResourceDetector.AtomType)
    val event3 = TestData.atomChangeEvent("jack")
    sendMessage(topic, event1)
    sendMessage(topic, event2)
    sendMessage(topic, event3)
    val actualCount = eventHandlerService.detectNewResources(10)
    actualCount should be(2)
  }

  def sendMessage(topic: String, event: AtomChangeEvent) = {
    val message = jsonMapper.serialize(event)
    log.debug(s"Send message: $message")
    val record = new ProducerRecord[String, String](topic, message)
    kafkaProducer.send(record).get
  }
}
