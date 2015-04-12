package nl.haploid.event.channel.service;

import nl.haploid.event.JsonMapper;
import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;
import nl.haploid.event.channel.repository.AtomChangeEventDmoRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class EventChannelService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AtomChangeEventDmoRepository repository;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private DmoToMessageMapperService mapperService;

	@Autowired
	private JsonMapper jsonMapper;

	@Transactional
	public int queueAtomChangeEvents() throws ExecutionException, InterruptedException, IOException {
		final List<AtomChangeEventDmo> eventDmos = repository.findAll();
		log.debug(String.format("Found %d row change eventDmos", eventDmos.size()));
		final List<AtomChangeEvent> events = mapperService.map(eventDmos);
		produceEvents(events);
		repository.delete(eventDmos);
		return events.size();
	}

	protected List<RecordMetadata> produceEvents(final List<AtomChangeEvent> events) throws ExecutionException, InterruptedException {
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
		final String topic = "test"; // TODO: test?
		final String message = jsonMapper.toString(event);
		final ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
		return kafkaProducer.send(record);
	}
}
