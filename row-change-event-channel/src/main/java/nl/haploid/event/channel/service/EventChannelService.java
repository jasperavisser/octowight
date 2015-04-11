package nl.haploid.event.channel.service;

import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import nl.haploid.event.channel.repository.RowChangeEventDmoRepository;
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
    private RowChangeEventDmoRepository repository;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Autowired
    private DmoMessageMapperService mapperService;

    @Autowired
    private JsonService jsonService;

    @Transactional
    public int queueRowChangeEvents() throws ExecutionException, InterruptedException, IOException {
        final List<RowChangeEventDmo> eventDmos = repository.findAll();
        log.debug(String.format("Found %d row change eventDmos", eventDmos.size()));
        final List<RowChangeEvent> events = mapperService.map(eventDmos);
        produceEvents(events);
        repository.delete(eventDmos);
        return events.size();
    }

    protected List<RecordMetadata> produceEvents(final List<RowChangeEvent> events) throws ExecutionException, InterruptedException {
        final List<Future<RecordMetadata>> futures = events.stream()
                .map(this::produceEvent)
                .collect(Collectors.toList());
        return futures.stream()
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

    protected Future<RecordMetadata> produceEvent(final RowChangeEvent event) {
        final String topic = "test";
        final String message;
        try {
            message = jsonService.toString(event);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert event to JSON!", e);
        }
        final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
        return kafkaProducer.send(record);
    }
}
