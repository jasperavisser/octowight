package nl.haploid.octowight.sample.service

import java.util.concurrent.{Future, TimeUnit}

import nl.haploid.octowight._
import nl.haploid.octowight.kafka.KafkaProducerFactory
import nl.haploid.octowight.sample.{AbstractTest, TestData}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}
import org.easymock.{EasyMock, IArgumentMatcher}
import org.springframework.test.util.ReflectionTestUtils

object ResourceProducerServiceTest {

  def isProducerRecord[T](topic: String, message: String): T = {
    EasyMock.reportMatcher(new IArgumentMatcher() {

      override def matches(argument: scala.Any): Boolean = {
        val producerRecord = argument.asInstanceOf[ProducerRecord[String, String]]
        producerRecord.topic == topic && producerRecord.value == message
      }

      override def appendTo(buffer: StringBuffer): Unit = {
        buffer.append(s"ProducerRecord[topic=$topic, message=$message]")
      }
    })
    null.asInstanceOf[T]
  }
}

class ResourceProducerServiceTest extends AbstractTest {
  @Tested private[this] val resourceProducerService: ResourceProducerService = null
  @Mocked private[this] val kafkaProducerFactory: KafkaProducerFactory = null
  @Mocked private[this] val jsonMapper: JsonMapper = null

  val topic: String = TestData.nextString

  override def beforeEach() = {
    super.beforeEach()
    ReflectionTestUtils.setField(resourceProducerService, "topic", topic)
  }

  it should "send resources" in {
    val kafkaProducer = mock[KafkaProducer[String, String]]
    val resource1 = TestData.resource
    val resource2 = TestData.resource
    val resources = Iterable(resource1, resource2)
    val message1 = TestData.nextString
    val message2 = TestData.nextString
    val future1 = mock[Future[RecordMetadata]]
    val future2 = mock[Future[RecordMetadata]]
    val recordMetadata1 = new RecordMetadata(null, 0, 1)
    val recordMetadata2 = new RecordMetadata(null, 1, 0)
    expecting {
      jsonMapper.serialize(resource1) andReturn message1 once()
      kafkaProducerFactory.kafkaProducer andReturn kafkaProducer once()
      kafkaProducer.send(ResourceProducerServiceTest.isProducerRecord(topic, message1)) andReturn future1 once()
      jsonMapper.serialize(resource2) andReturn message2 once()
      kafkaProducer.send(ResourceProducerServiceTest.isProducerRecord(topic, message2)) andReturn future2 once()
      future1.get(5, TimeUnit.SECONDS) andReturn recordMetadata1 once()
      future2.get(5, TimeUnit.SECONDS) andReturn recordMetadata2 once()
    }
    whenExecuting(jsonMapper, kafkaProducerFactory, kafkaProducer, future1, future2) {
      resourceProducerService.send(resources)
    }
  }
}
