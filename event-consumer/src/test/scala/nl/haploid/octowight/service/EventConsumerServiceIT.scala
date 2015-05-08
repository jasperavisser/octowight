package nl.haploid.octowight.service

import java.util
import java.util.concurrent.TimeUnit

import nl.haploid.octowight.{AbstractIT, AtomChangeEvent, JsonMapper, TestData}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.junit.Rule
import org.junit.rules.Timeout
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

class EventConsumerServiceIT extends AbstractIT {
  private val log = LoggerFactory.getLogger(getClass)

  @Autowired private val service: EventConsumerService = null
  @Autowired private val kafkaProducer: KafkaProducer[String, String] = null
  @Autowired private val jsonMapper: JsonMapper = null

  @Rule var globalTimeout = new Timeout(10, TimeUnit.SECONDS)

  def sendMessage(topic: String, event: AtomChangeEvent) {
    val message = jsonMapper.serialize(event)
    log.debug(s"Send message: $message")
    val record = new ProducerRecord[String, String](topic, message)
    kafkaProducer.send(record).get
  }

  "Event consumer" should "consume a message" in {
    val topic = TestData.topic
    service.setTopic(topic)
    val expectedEvent = TestData.atomChangeEvent("joan")
    sendMessage(topic, expectedEvent)
    val actualEvent = service.consumeMessage
    actualEvent should be(expectedEvent)
  }

  "Event consumer" should "consume multiple messages" in {
    val topic = TestData.topic
    service.setTopic(topic)
    val event1 = TestData.atomChangeEvent("bob")
    val event2 = TestData.atomChangeEvent("benson")
    val expectedEvents = util.Arrays.asList(event1, event2)
    sendMessage(topic, event1)
    sendMessage(topic, event2)
    val actualEvents = service.consumeMessages()
    actualEvents should be(expectedEvents)
  }

  "Event consumer" should "commit stream offset" in {
    val topic = TestData.topic
    service.setTopic(topic)
    val event = TestData.atomChangeEvent("harris")
    sendMessage(topic, event)
    service.consumeMessages()
    service.commit()
    service.reset()
    val actualEvents = service.consumeMessages()
    actualEvents should have size 0
  }
}
