package nl.haploid.octowight.registry.configuration

import com.mongodb.MongoClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.{MongoTemplate, SimpleMongoDbFactory}
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = Array("nl.haploid.octowight.registry.repository"))
class MongoConfiguration {

  @Value("${octowight.registry.mongo.database}") private[this] val mongoDatabase: String = null
  @Value("${octowight.registry.mongo.hostname}") private[this] val mongoHostname: String = null
  @Value("${octowight.registry.mongo.port}") private[this] val mongoPort: Integer = null

  def getMongoDatabase = mongoDatabase

  @Bean
  def mongoDbFactory: MongoDbFactory = {
    val mongoClient = new MongoClient(mongoHostname, mongoPort)
    new SimpleMongoDbFactory(mongoClient, mongoDatabase)
  }

  @Bean
  def mongoTemplate(mongoDbFactory: MongoDbFactory) = new MongoTemplate(mongoDbFactory)
}
