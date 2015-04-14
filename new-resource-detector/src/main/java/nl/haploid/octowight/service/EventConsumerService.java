package nl.haploid.octowight.service;

import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.kafka.KafkaConsumerFactory;
import nl.haploid.octowight.kafka.KafkaStreamSpliterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EventConsumerService {

	@Value("${kafka.topic.events}")
	private String topic;

	@Autowired
	private KafkaConsumerFactory consumerFactoryService;

	@Autowired
	private JsonMapper jsonMapper;

	private ThreadLocal<ConsumerConnector> kafkaConsumer;

	private ThreadLocal<KafkaStream<byte[], byte[]>> stream;

	protected KafkaStream<byte[], byte[]> getStream() {
		if (stream == null) {
			stream = ThreadLocal.withInitial(() -> this.consumerFactoryService.createStream(getKafkaConsumer(), getTopic()));
		}
		return stream.get();
	}

	protected ConsumerConnector getKafkaConsumer() {
		if (kafkaConsumer == null) {
			kafkaConsumer = ThreadLocal.withInitial(this.consumerFactoryService::createKafkaConsumer);
		}
		return kafkaConsumer.get();
	}

	private String getTopic() {
		return topic;
	}

	protected void setTopic(final String topic) {
		this.topic = topic;
		reset();
	}

	public AtomChangeEvent consumeMessage() {
		final String message = new String(getStream().iterator().next().message());
		return jsonMapper.parse(message, AtomChangeEvent.class);
	}

	public Stream<AtomChangeEvent> consumeMessages(final int batchSize) {
		return StreamSupport.stream(new KafkaStreamSpliterator(getStream()), false)
				.limit(batchSize)
				.map(messageAndMetadata -> new String(messageAndMetadata.message()))
				.map(message -> jsonMapper.parse(message, AtomChangeEvent.class));
	}

	public void commit() {
		getKafkaConsumer().commitOffsets();
	}

	protected void reset() {
		if (kafkaConsumer != null) {
			kafkaConsumer.get().shutdown();
		}
		kafkaConsumer = null;
		stream = null;
	}
}