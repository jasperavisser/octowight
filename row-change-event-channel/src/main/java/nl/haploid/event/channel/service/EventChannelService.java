package nl.haploid.event.channel.service;

import nl.haploid.event.channel.repository.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class EventChannelService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RowChangeEventRepository repository;

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Scheduled(fixedRate = 500)
    @Transactional
    public void queueRowChangeEvents() throws ExecutionException, InterruptedException {
        log.debug("Queue row change events");
        List<RowChangeEvent> events = repository.findAll();
        log.debug(String.format("Found %d row change events", events.size()));
        List<Future<RecordMetadata>> futures = new ArrayList<Future<RecordMetadata>>();
        for (RowChangeEvent event : events) {
            String topic = "test";
            String message = event.toString();
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
            Future<RecordMetadata> future = kafkaProducer.send(record);
            futures.add(future);
        }
        for (Future<RecordMetadata> future : futures) {
            future.get();
        }
        repository.delete(events);
    }
}
