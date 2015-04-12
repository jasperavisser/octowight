package nl.haploid.resource.detector.service;

import nl.haploid.event.JsonMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ResourceProducerService {

    @Autowired
    private KafkaProducer<String, String> kafkaProducer;

    @Autowired
    private JsonMapper jsonMapper;

    // TODO: unit test/IT
    public Future<RecordMetadata> publishResourceDescriptor(final ResourceDescriptor descriptor) {
        final String topic = "TODO"; // TODO: topic name
        final String message = jsonMapper.toString(descriptor);
        return kafkaProducer.send(new ProducerRecord<>(topic, message));
    }

    // TODO: unit test/IT
    public RecordMetadata resolveFuture(final Future<RecordMetadata> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Could not resolve future; message may not have been produced!", e);
        }
    }
}
