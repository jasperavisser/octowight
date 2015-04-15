package nl.haploid.octowight.service;

import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.data.ResourceCoreAtom;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class DirtyResourceProducerService {

	@Value("${octowight.kafka.topic.resources.dirty}")
	private String topic;

	@Autowired
	private KafkaProducer<String, String> kafkaProducer;

	@Autowired
	private JsonMapper jsonMapper;

	public Future<RecordMetadata> sendDirtyResource(final ResourceCoreAtom coreAtom) {
		final String message = jsonMapper.toString(coreAtom);
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
