package nl.haploid.octowight.configuration;

import kafka.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

	@Value("${octowight.kafka.hostname}")
	private String kafkaHostname;

	@Value("${octowight.kafka.port}")
	private int kafkaPort;

	@Value("${octowight.kafka.consumer.timeout.ms}")
	private Integer consumerTimeoutMs;

	@Value("${octowight.kafka.group.id}")
	private String kafkaGroupId;

	@Value("${octowight.zookeeper.hostname}")
	private String zookeeperHostname;

	@Value("${octowight.zookeeper.port}")
	private int zookeeperPort;

	@Bean
	public ConsumerConfig consumerConfig() {
		final Properties properties = new Properties();
		properties.put("zookeeper.connect", String.format("%s:%d", zookeeperHostname, zookeeperPort));
		properties.put("group.id", kafkaGroupId);
		properties.put("zookeeper.session.timeout.ms", "400");
		properties.put("zookeeper.sync.time.ms", "200");
		properties.put("auto.commit.enable", "false");
		properties.put("auto.offset.reset", "smallest");
		properties.put("consumer.timeout.ms", consumerTimeoutMs.toString());
		return new ConsumerConfig(properties);
	}

	@Bean
	public KafkaProducer<String, String> kafkaProducer() {
		final Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%d", kafkaHostname, kafkaPort));
		properties.put(ProducerConfig.RETRIES_CONFIG, "3");
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
		properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 200);
		properties.put(ProducerConfig.BLOCK_ON_BUFFER_FULL_CONFIG, true);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new KafkaProducer<>(properties);
	}
}
