package nl.haploid.octowight.kafka

import nl.haploid.octowight.{AbstractIT, TestData}
import org.apache.kafka.clients.producer.ProducerRecord
import org.scalatest.OneInstancePerTest
import org.springframework.beans.factory.annotation.Autowired

class KafkaConsumerIT extends AbstractIT with OneInstancePerTest {
  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Autowired private[this] val kafkaProducerFactory: KafkaProducerFactory = null

  private[this] val topic = TestData.nextString

  behavior of "Kafka consumer"

  it should "get the next N messages" in {
    val message1 = TestData.nextString
    val message2 = TestData.nextString
    val kafkaProducer = kafkaProducerFactory.kafkaProducer
    kafkaProducer.send(new ProducerRecord[String, String](topic, message1))
    kafkaProducer.send(new ProducerRecord[String, String](topic, message2))
    kafkaProducer.close()
    val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)
    kafkaConsumer.nextMessages(10) should be(Iterable(message1, message2))
    kafkaConsumer.shutdown()
  }

  it should "get the next message" in {
    val message = TestData.nextString
    val kafkaProducer = kafkaProducerFactory.kafkaProducer
    kafkaProducer.send(new ProducerRecord[String, String](topic, message))
    kafkaProducer.close()
    val kafkaConsumer = kafkaConsumerFactory.kafkaConsumer(topic)
    kafkaConsumer.nextMessage should be(message)
    kafkaConsumer.shutdown()
  }

  it should "commit offsets" in {
    val message1 = TestData.nextString
    val message2 = TestData.nextString
    val kafkaProducer = kafkaProducerFactory.kafkaProducer
    kafkaProducer.send(new ProducerRecord[String, String](topic, message1))
    kafkaProducer.send(new ProducerRecord[String, String](topic, message2))
    kafkaProducer.close()
    val kafkaConsumer1 = kafkaConsumerFactory.kafkaConsumer(topic)
    kafkaConsumer1.nextMessage should be(message1)
    kafkaConsumer1.commit()
    kafkaConsumer1.shutdown()
    val kafkaConsumer2 = kafkaConsumerFactory.kafkaConsumer(topic)
    kafkaConsumer2.nextMessage should be(message2)
    kafkaConsumer2.shutdown()
  }
}
