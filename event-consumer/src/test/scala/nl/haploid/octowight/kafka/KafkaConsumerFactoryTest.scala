package nl.haploid.octowight.kafka

import java.util
import java.util.Collections

import _root_.kafka.consumer.{ConsumerConfig, KafkaStream}
import _root_.kafka.javaapi.consumer.ConsumerConnector
import nl.haploid.octowight._
import org.easymock.EasyMock

class KafkaConsumerFactoryTest extends AbstractTest {
  @Tested private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Mocked private[this] val consumerConfig: ConsumerConfig = null

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
      val actualStream = kafkaConsumerFactory.createStream(kafkaConsumer, topic)
      actualStream should be(expectedStream)
    }
  }
}
