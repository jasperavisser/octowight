package nl.haploid.octowight.sample.configuration

import com.jolbox.bonecp.BoneCPDataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration, PropertySources}

@Configuration
@PropertySources(value = Array())
class PostgresConfiguration {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @Value("${octowight.postgres.hostname}") private[this] val hostname: String = null
  @Value("${octowight.postgres.port}") private[this] val port: Int = 0
  @Value("${octowight.postgres.username}") private[this] val username: String = null
  @Value("${octowight.postgres.database}") private[this] val database: String = null

  @Bean def dataSource = {
    val dataSource = new BoneCPDataSource
    dataSource.setDriverClass("org.postgresql.Driver")
    val jdbcUrl = s"jdbc:postgresql://$hostname:$port/$database"
    dataSource.setJdbcUrl(jdbcUrl)
    dataSource.setUsername(username)
    dataSource.setPassword(username)
    log.debug(s"Will connect to $jdbcUrl as $username")
    dataSource
  }
}
