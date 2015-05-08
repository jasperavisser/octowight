package nl.haploid.octowight.kafka

import nl.haploid.octowight.{AbstractIT, TestData}
import org.springframework.beans.factory.annotation.Autowired

class KafkaConsumerFactoryIT extends AbstractIT {
  @Autowired private val kafkaConsumerFactory: KafkaConsumerFactory = null

  "Kafka consumer factory" should "build a consumer" in {
    val kafkaConsumer = kafkaConsumerFactory.createKafkaConsumer
    kafkaConsumer should not be null
  }

  "Kafka consumer factory" should "build a stream" in {
    val topic = TestData.topic
    val kafkaConsumer = kafkaConsumerFactory.createKafkaConsumer
    val stream = kafkaConsumerFactory.createStream(kafkaConsumer, topic)
    stream should not be null
  }
}
