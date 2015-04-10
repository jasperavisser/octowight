package nl.haploid.resource.detector;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;
import java.util.UUID;

@Configuration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfiguration.class)
})
public class TestConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setProperties(getTestProperties());
        return configurer;
    }

    private static Properties getTestProperties() {
        final Properties properties = new Properties();
        final String dockerHostIp = System.getenv("DOCKER_HOST_IP");
        final String topic = UUID.randomUUID().toString();
        properties.setProperty("postgres.hostname", dockerHostIp);
        properties.setProperty("postgres.port", "5432");
        properties.setProperty("postgres.database", "postgres");
        properties.setProperty("postgres.username", "postgres");
        properties.setProperty("kafka.topic", topic);
        properties.setProperty("kafka.hostname", dockerHostIp);
        properties.setProperty("zookeeper.hostname", dockerHostIp);
        return properties;
    }
}
