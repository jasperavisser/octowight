package nl.haploid.octowight.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KafkaConsumerFactory {

	@Autowired
	private ConsumerConfig consumerConfig;

	public ConsumerConnector createKafkaConsumer() {
		return Consumer.createJavaConsumerConnector(consumerConfig);
	}

	public KafkaStream<byte[], byte[]> createStream(final ConsumerConnector kafkaConsumer, final String topic) {
		final Map<String, Integer> topicCountMap = new HashMap<>();
		topicCountMap.put(topic, 1);
		final Map<String, List<KafkaStream<byte[], byte[]>>> streamsPerTopic = kafkaConsumer.createMessageStreams(topicCountMap);
		return streamsPerTopic.get(topic).get(0);
	}
}
