package nl.haploid.octowight.service

import kafka.consumer.KafkaStream
import kafka.javaapi.consumer.ConsumerConnector
import nl.haploid.octowight.kafka.{KafkaConsumerFactory, KafkaStreamIterator}
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
class EventConsumerService {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val consumerFactoryService: KafkaConsumerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  @Value("${octowight.kafka.topic.events}") private[this] var topic: String = null

  private[this] var kafkaConsumer: ThreadLocal[ConsumerConnector] = null
  private[this] var stream: ThreadLocal[KafkaStream[Array[Byte], Array[Byte]]] = null

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

  // TODO: topic should be read only; we only write to it for IT
  def setTopic(topic: String) = {
    this.topic = topic
    reset()
  }

  def consumeEvent = {
    val message = new String(getStream.iterator().next().message())
    parseMessage(message)
  }

  // TODO: reinstate batch size?
  def consumeDistinctEvents(): Set[AtomChangeEvent] = {
    val events = new KafkaStreamIterator(getStream)
      .map(m => new String(m.message()))
      .map(parseMessage)
      .toIterable
    log.debug(s"Consumed ${events.size} events")
    events
      .groupBy(f => (f.getAtomId, f.getAtomOrigin, f.getAtomType))
      .map { case (_, similarEvents) => similarEvents.head }
      .toSet
  }

  protected def parseMessage(message: String) = {
    log.debug(s"Consume message: $message")
    jsonMapper.deserialize(message, classOf[AtomChangeEvent])
  }

  def commit() = getKafkaConsumer.commitOffsets()

  def reset() = {
    if (kafkaConsumer != null) {
      kafkaConsumer.get.shutdown()
    }
    kafkaConsumer = null
    stream = null
  }
}
