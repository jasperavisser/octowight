package nl.haploid.octowight.registry.configuration;

import nl.haploid.octowight.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "nl.haploid.octowight.registry")
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
		properties.setProperty("octowight.registry.postgres.hostname", dockerHostIp);
		properties.setProperty("octowight.registry.postgres.port", "5433");
		properties.setProperty("octowight.registry.postgres.database", "postgres");
		properties.setProperty("octowight.registry.postgres.username", "postgres");
		return properties;
	}
}