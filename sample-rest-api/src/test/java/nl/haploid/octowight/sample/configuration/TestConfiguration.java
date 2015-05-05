package nl.haploid.octowight.sample.configuration;

import nl.haploid.octowight.sample.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "nl.haploid.octowight")
public class TestConfiguration {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		final Properties properties = new Properties();
		final String dockerHostIp = System.getenv("DOCKER_HOST_IP");
		properties.setProperty("octowight.postgres.hostname", dockerHostIp);
		properties.setProperty("octowight.postgres.port", "5432");
		properties.setProperty("octowight.postgres.database", "postgres");
		properties.setProperty("octowight.postgres.username", "postgres");
		properties.setProperty("octowight.registry.mongo.hostname", dockerHostIp);
		properties.setProperty("octowight.registry.mongo.port", "27017");
		properties.setProperty("octowight.registry.mongo.database", String.format("integration-test-%s", TestData.nextString()));
		configurer.setProperties(properties);
		return configurer;
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
