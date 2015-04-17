package nl.haploid.octowight.configuration;

import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "nl.haploid.octowight", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfiguration.class)
})
public class TestConfiguration {

	@Autowired
	@Qualifier("sample")
	private DataSource dataSource;

	@Autowired
	@Qualifier("sample")
	private PlatformTransactionManager transactionManager;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		final Properties properties = new Properties();
		final String dockerHostIp = System.getenv("DOCKER_HOST_IP");
		properties.setProperty("octowight.kafka.topic.events", TestData.topic());
		properties.setProperty("octowight.kafka.topic.resources.dirty", TestData.topic());
		properties.setProperty("octowight.kafka.hostname", dockerHostIp);
		properties.setProperty("octowight.kafka.port", "9092");
		properties.setProperty("octowight.kafka.consumer.timeout.ms", "2500");
		properties.setProperty("octowight.postgres.hostname", dockerHostIp);
		properties.setProperty("octowight.postgres.port", "5432");
		properties.setProperty("octowight.postgres.database", "postgres");
		properties.setProperty("octowight.postgres.username", "postgres");
		properties.setProperty("octowight.registry.postgres.hostname", dockerHostIp);
		properties.setProperty("octowight.registry.postgres.port", "5433");
		properties.setProperty("octowight.registry.postgres.database", "postgres");
		properties.setProperty("octowight.registry.postgres.username", "postgres");
		properties.setProperty("octowight.zookeeper.hostname", dockerHostIp);
		properties.setProperty("octowight.zookeeper.port", "2181");
		configurer.setProperties(properties);
		return configurer;
	}

	@Bean
	public JsonMapper jsonMapper() {
		return new JsonMapper();
	}

	@Bean
	public DataSource dataSource() {
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return transactionManager;
	}
}
