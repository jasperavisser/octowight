package nl.haploid.octowight.service

import kafka.consumer.KafkaStream
import kafka.javaapi.consumer.ConsumerConnector
import nl.haploid.octowight.kafka.{KafkaConsumerFactory, KafkaStreamIterator}
import nl.haploid.octowight.{AtomChangeEvent, JsonMapper}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Service

@Service
// TODO: @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class EventConsumerService {
  protected[this] val log = LoggerFactory.getLogger(getClass)

  @Autowired private[this] val kafkaConsumerFactory: KafkaConsumerFactory = null
  @Autowired private[this] val jsonMapper: JsonMapper = null

  @Value("${octowight.kafka.topic.events}") private[this] var topic: String = null

  private[this] var consumerConnectorOption: Option[ConsumerConnector] = None
  private[this] var streamOption: Option[KafkaStream[Array[Byte], Array[Byte]]] = None

  def getTopic = topic

  def stream: KafkaStream[Array[Byte], Array[Byte]] = {
    streamOption.getOrElse {
      log.debug(s"Create new stream for $getTopic}")
      val stream: KafkaStream[Array[Byte], Array[Byte]] = kafkaConsumerFactory.createStream(consumerConnector, getTopic)
      streamOption = Some(stream)
      stream
    }
  }

  def consumerConnector: ConsumerConnector = {
    consumerConnectorOption.getOrElse {
      val consumerConnector: ConsumerConnector = kafkaConsumerFactory.createKafkaConsumer
      consumerConnectorOption = Some(consumerConnector)
      consumerConnector
    }
  }

  def consumeEvent = {
    val message = new String(stream.iterator().next().message())
    parseMessage(message)
  }

  // TODO: reinstate batch size?
  def consumeDistinctEvents(): Set[AtomChangeEvent] = {
    val events = new KafkaStreamIterator(stream)
      .map(m => new String(m.message()))
      .map(parseMessage)
      .toIterable
    log.debug(s"Consumed ${events.size} events")
    events
      .groupBy(e => (e.atomId, e.atomOrigin, e.atomCategory))
      .map { case (_, similarEvents) => similarEvents.head }
      .toSet
  }

  protected def parseMessage(message: String) = {
    log.debug(s"Consume message: $message")
    jsonMapper.deserialize(message, classOf[AtomChangeEvent])
  }

  def commit() = consumerConnector.commitOffsets()

  def reset(topic: String) = {
    log.warn(s"Reset consumer to topic $topic")
    this.topic = topic
    consumerConnectorOption match {
      case Some(consumerConnector) => consumerConnector.shutdown()
      case None =>
    }
    consumerConnectorOption = None
    streamOption = None
  }
}
