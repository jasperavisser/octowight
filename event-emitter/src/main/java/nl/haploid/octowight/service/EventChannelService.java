package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class EventChannelService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${octowight.kafka.topic.events}")
	private String topic;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private JsonMapper jsonMapper;

	public List<RecordMetadata> sendEvents(final List<AtomChangeEvent> events) {
		log.debug(String.format("Send %d messages", events.size()));
		return events.stream()
				.map(this::sendEvent)
				.collect(Collectors.toList()).stream()
				.map(this::resolveFuture)
				.collect(Collectors.toList());
	}

	protected Future<RecordMetadata> sendEvent(final AtomChangeEvent event) {
		final String message = jsonMapper.toString(event);
		log.debug(String.format("Send message to %s: %s", topic, message));
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
		return kafkaProducer.send(record);
	}

	private RecordMetadata resolveFuture(final Future<RecordMetadata> future) {
		try {
			final RecordMetadata recordMetadata = future.get();
			return recordMetadata;
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Could not resolve future; message may not have been produced!", e);
		}
	}
}
