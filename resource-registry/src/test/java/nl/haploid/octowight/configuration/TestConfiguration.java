package nl.haploid.octowight.configuration;

import nl.haploid.octowight.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "nl.haploid.octowight")
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
		properties.setProperty("octowight.postgres.hostname", dockerHostIp);
		properties.setProperty("octowight.postgres.port", "5432");
		properties.setProperty("octowight.postgres.database", "postgres");
		properties.setProperty("octowight.postgres.username", "postgres");
		return properties;
	}
}
