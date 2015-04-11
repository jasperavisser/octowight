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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    protected List<RecordMetadata> produceEvents(final List<RowChangeEvent> events) throws ExecutionException, InterruptedException, IOException {
        final List<Future<RecordMetadata>> futures = new ArrayList<Future<RecordMetadata>>();
        for (final RowChangeEvent event : events) {
            futures.add(produceEvent(event));
        }
        final List<RecordMetadata> results = new ArrayList<RecordMetadata>();
        for (final Future<RecordMetadata> future : futures) {
            results.add(future.get());
        }
        return results;
    }

    protected Future<RecordMetadata> produceEvent(final RowChangeEvent event) throws IOException {
        final String topic = "test";
        final String message = jsonService.toString(event);
        final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
        return kafkaProducer.send(record);
    }
}
