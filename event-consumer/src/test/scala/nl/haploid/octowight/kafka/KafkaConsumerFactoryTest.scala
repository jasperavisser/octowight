package nl.haploid.octowight.kafka

import java.util
import java.util.Collections

import kafka.consumer.{ConsumerConfig, KafkaStream}
import kafka.javaapi.consumer.ConsumerConnector
import nl.haploid.octowight.{AbstractTest, Mocked, TestData, Tested}
import org.easymock.EasyMock

class KafkaConsumerFactoryTest extends AbstractTest {
  @Tested private val kafkaConsumerFactory = new KafkaConsumerFactory
  @Mocked private val consumerConfig: ConsumerConfig = null

  "Kafka consumer factory" should "build a stream" in {
    val kafkaConsumer = mock[ConsumerConnector]
    val expectedStream = mock[KafkaStream[Array[Byte], Array[Byte]]]
    val topic = TestData.topic
    val streams = new util.HashMap[String, util.List[KafkaStream[Array[Byte], Array[Byte]]]]
    streams.put(topic, Collections.singletonList(expectedStream))
    expecting {
      kafkaConsumer.createMessageStreams(EasyMock.anyObject(classOf[util.Map[String, Integer]])) andReturn streams once()
    }
    whenExecuting(kafkaConsumer) {
      val actualStream: KafkaStream[Array[Byte], Array[Byte]] = kafkaConsumerFactory.createStream(kafkaConsumer, topic)
      actualStream should be(expectedStream)
    }
  }
}
