package nl.haploid.octowight.registry.configuration;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories(basePackages = "nl.haploid.octowight.registry.repository")
public class MongoConfiguration {

	// TODO: drop IT database after tests

	@Value("${octowight.registry.mongo.database}")
	private String mongoDatabase;

	@Value("${octowight.registry.mongo.hostname}")
	private String mongoHostname;

	@Value("${octowight.registry.mongo.port}")
	private Integer mongoPort;

	public String getMongoDatabase() {
		return mongoDatabase;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		final MongoClient mongoClient = new MongoClient(mongoHostname, mongoPort);
		return new SimpleMongoDbFactory(mongoClient, mongoDatabase);
	}

	@Bean
	public MongoTemplate mongoTemplate(final MongoDbFactory mongoDbFactory) {
		return new MongoTemplate(mongoDbFactory);
	}
}
