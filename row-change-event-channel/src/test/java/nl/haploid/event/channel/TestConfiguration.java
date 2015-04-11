package nl.haploid.event.channel;

import nl.haploid.event.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Properties;

@Configuration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfiguration.class)
})
public class TestConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        final Properties properties = new Properties();
        final String dockerHostIp = System.getenv("DOCKER_HOST_IP");
        properties.setProperty("postgres.hostname", dockerHostIp);
        properties.setProperty("postgres.port", "5432");
        properties.setProperty("postgres.database", "postgres");
        properties.setProperty("postgres.username", "postgres");
        properties.setProperty("kafka.hostname", dockerHostIp);
        configurer.setProperties(properties);
        return configurer;
    }

    @Bean
    public JsonMapper jsonMapper() {
        return new JsonMapper();
    }
}
