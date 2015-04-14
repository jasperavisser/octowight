package nl.haploid.octowight.service;

import nl.haploid.octowight.JsonMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class ResourceProducerService {

	@Value("${kafka.topic.resources}")
	private String topic;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private JsonMapper jsonMapper;

	public Future<RecordMetadata> publishResourceDescriptor(final ResourceDescriptor descriptor) {
		final String message = jsonMapper.toString(descriptor);
		return kafkaProducer.send(new ProducerRecord<>(topic, message));
	}

	public RecordMetadata resolveFuture(final Future<RecordMetadata> future) {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException("Could not resolve future; message may not have been produced!", e);
		}
	}
}
