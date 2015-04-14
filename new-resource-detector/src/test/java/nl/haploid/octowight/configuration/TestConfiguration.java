package nl.haploid.octowight.configuration;

import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.TestData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "nl.haploid.octowight", excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfiguration.class)
})
public class TestConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setProperties(getTestProperties());
		return configurer;
	}

	@Bean
	public JsonMapper jsonMapper() {
		return new JsonMapper();
	}

	private static Properties getTestProperties() {
		final Properties properties = new Properties();
		final String dockerHostIp = System.getenv("DOCKER_HOST_IP");
		properties.setProperty("postgres.hostname", dockerHostIp);
		properties.setProperty("postgres.port", "5432");
		properties.setProperty("postgres.database", "postgres");
		properties.setProperty("postgres.username", "postgres");
		properties.setProperty("redis.hostname", dockerHostIp);
		properties.setProperty("kafka.topic.events", TestData.topic());
		properties.setProperty("kafka.topic.resources", TestData.topic());
		properties.setProperty("kafka.hostname", dockerHostIp);
		properties.setProperty("kafka.consumer.timeout.ms", "2500");
		properties.setProperty("zookeeper.hostname", dockerHostIp);
		return properties;
	}
}
