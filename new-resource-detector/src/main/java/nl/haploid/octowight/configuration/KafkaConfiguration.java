package nl.haploid.octowight.configuration;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

	@Value("${kafka.hostname:localhost}")
	private String kafkaHostname;

	@Value("${kafka.port:9092}")
	private int kafkaPort;

	@Value("${kafka.consumer.timeout.ms:200}")
	private Integer consumerTimeoutMs;

	@Value("${zookeeper.hostname:localhost}")
	private String zookeeperHostname;

	@Value("${zookeeper.port:2181}")
	private int zookeeperPort;

	public String getKafkaHostname() {
		return kafkaHostname;
	}

	public int getKafkaPort() {
		return kafkaPort;
	}

	public String getZookeeperHostname() {
		return zookeeperHostname;
	}

	public int getZookeeperPort() {
		return zookeeperPort;
	}

	@Bean(destroyMethod = "shutdown")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ConsumerConnector kafkaConsumer(final ConsumerConfig consumerConfig) {
		return Consumer.createJavaConsumerConnector(consumerConfig);
	}

	@Bean
	public ConsumerConfig consumerConfig() {
		final Properties properties = new Properties();
		properties.put("zookeeper.connect", String.format("%s:%d", getZookeeperHostname(), getZookeeperPort()));
		properties.put("group.id", "1");
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
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%d", getKafkaHostname(), getKafkaPort()));
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
