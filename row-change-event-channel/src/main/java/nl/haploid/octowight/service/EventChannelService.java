package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.repository.AtomChangeEventDmo;
import nl.haploid.octowight.repository.AtomChangeEventDmoRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private AtomChangeEventDmoRepository repository;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private DmoToMessageMapperService mapperService;

	@Autowired
	private JsonMapper jsonMapper;

	@Transactional
	public int queueAtomChangeEvents() throws ExecutionException, InterruptedException {
		final List<AtomChangeEventDmo> eventDmos = repository.findAll();
		log.debug(String.format("Found %d row change eventDmos", eventDmos.size()));
		final List<AtomChangeEvent> events = mapperService.map(eventDmos);
		produceEvents(events);
		repository.delete(eventDmos);
		return events.size();
	}

	protected List<RecordMetadata> produceEvents(final List<AtomChangeEvent> events) {
		return events.stream()
				.map(this::produceEvent)
				.collect(Collectors.toList()).stream()
				.map(this::resolveFuture)
				.collect(Collectors.toList());
	}

	private RecordMetadata resolveFuture(Future<RecordMetadata> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Could not resolve future; message may not have been produced!", e);
		}
	}

	protected Future<RecordMetadata> produceEvent(final AtomChangeEvent event) {
		final String message = jsonMapper.toString(event);
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
		return kafkaProducer.send(record);
	}
}
