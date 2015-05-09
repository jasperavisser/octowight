package nl.haploid.octowight.service

import kafka.consumer.KafkaStream
import kafka.javaapi.consumer.ConsumerConnector
import nl.haploid.octowight.kafka.{KafkaConsumerFactory, KakfaStreamIterator}
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

// TODO: with autowired constructors, we can get rid of null assignments
@Service
class EventConsumerService {
  private val log = LoggerFactory.getLogger(getClass)

  @Autowired private val consumerFactoryService: KafkaConsumerFactory = null
  @Autowired private val jsonMapper: JsonMapper = null

  @Value("${octowight.kafka.topic.events}") private var topic: String = null

  private var kafkaConsumer: ThreadLocal[ConsumerConnector] = null
  private var stream: ThreadLocal[KafkaStream[Array[Byte], Array[Byte]]] = null

  def getStream = {
    if (stream == null) {
      log.debug(s"Create new stream for $getTopic}")
      stream = new ThreadLocal[KafkaStream[Array[Byte], Array[Byte]]]
      stream.set(consumerFactoryService.createStream(getKafkaConsumer, getTopic))
    }
    stream.get
  }

  def getKafkaConsumer = {
    if (kafkaConsumer == null) {
      kafkaConsumer = new ThreadLocal[ConsumerConnector]
      kafkaConsumer.set(consumerFactoryService.createKafkaConsumer)
    }
    kafkaConsumer.get
  }

  def getTopic = topic

  def setTopic(topic: String) {
    this.topic = topic
    reset()
  }

  def consumeMessage = {
    val message = new String(getStream.iterator().next().message())
    parseMessage(message)
  }

  // TODO: reinstate batch size?
  def consumeMessages() = {
    new KakfaStreamIterator(getStream)
      .map(m => new String(m.message()))
      .map(parseMessage)
      .toList
      .asJava // TODO: after we convert other projects, this can be a scala iteratable
  }

  protected def parseMessage(message: String) = {
    // TODO: log.debug(s"Consume message: $message")
    jsonMapper.deserialize(message, classOf[AtomChangeEvent])
  }

  def commit() = getKafkaConsumer.commitOffsets()

  def reset() {
    if (kafkaConsumer != null) {
      kafkaConsumer.get.shutdown()
    }
    kafkaConsumer = null
    stream = null
  }
}
