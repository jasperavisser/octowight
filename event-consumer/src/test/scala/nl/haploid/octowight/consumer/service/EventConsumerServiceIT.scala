package nl.haploid.octowight.consumer.service

import java.util.concurrent.TimeUnit

import nl.haploid.octowight.consumer.{AbstractIT, TestData}
import nl.haploid.octowight.kafka.producer.KafkaOutChannel
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.Rule
import org.junit.rules.Timeout
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.util.ReflectionTestUtils

class EventConsumerServiceIT extends AbstractIT with KafkaOutChannel {
  private[this] lazy val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val eventConsumerService1: EventConsumerService = null
  @Autowired private[this] val eventConsumerService2: EventConsumerService = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  @Rule val globalTimeout = new Timeout(10, TimeUnit.SECONDS)

  private[this] val topic = TestData.nextString

  behavior of "Event consumer service"

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
    val expectedEvent = TestData.atomChangeEvent(TestData.nextString)
    sendMessage(topic, expectedEvent)
    val actualEvent = eventConsumerService1.consumeEvent
    actualEvent should be(expectedEvent)
  }

  it should "consume multiple messages" in {
    val event1 = TestData.atomChangeEvent(TestData.nextString)
    val event2 = TestData.atomChangeEvent(TestData.nextString)
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
