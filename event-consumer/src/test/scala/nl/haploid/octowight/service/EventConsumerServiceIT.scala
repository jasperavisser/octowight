package nl.haploid.octowight.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.{AbstractIT, AtomChangeEvent, JsonMapper, TestData}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.junit.Rule
import org.junit.rules.Timeout
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.util.ReflectionTestUtils

class EventConsumerServiceIT extends AbstractIT {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventConsumerService1: EventConsumerService = null
  @Autowired private[this] val eventConsumerService2: EventConsumerService = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  @Rule val globalTimeout = new Timeout(10, TimeUnit.SECONDS)

  private[this] lazy val kafkaProducer = kafkaProducerFactory.kafkaProducer

  private[this] val topic = TestData.topic

  override def beforeEach() = {
    log.info(s"Topic is $topic")
    super.beforeEach()
    ReflectionTestUtils.setField(eventConsumerService1, "topic", topic)
  }

  private[this] def sendMessage(topic: String, event: AtomChangeEvent): Unit = {
    val message = jsonMapper.serialize(event)
    log.debug(s"Send message: $message")
    val record = new ProducerRecord[String, String](topic, message)
    kafkaProducer.send(record).get
  }

  it should "consume a message" in {
    val expectedEvent = TestData.atomChangeEvent("joan")
    sendMessage(topic, expectedEvent)
    val actualEvent = eventConsumerService1.consumeEvent
    actualEvent should be(expectedEvent)
  }

  it should "consume multiple messages" in {
    val event1 = TestData.atomChangeEvent("bob")
    val event2 = TestData.atomChangeEvent("benson")
    val expectedEvents = Set(event1, event2)
    sendMessage(topic, event1)
    sendMessage(topic, event2)
    val actualEvents = eventConsumerService1.consumeDistinctEvents()
    actualEvents should be(expectedEvents)
  }

  it should "commit stream offset" in {
    val event = TestData.atomChangeEvent("harris")
    sendMessage(topic, event)
    eventConsumerService1.consumeDistinctEvents()
    eventConsumerService1.commit()
    val actualEvents = eventConsumerService2.consumeDistinctEvents()
    actualEvents should have size 0
  }
}
