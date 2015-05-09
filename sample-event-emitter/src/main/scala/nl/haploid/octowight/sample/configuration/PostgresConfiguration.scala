package nl.haploid.octowight.sample.configuration

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import com.jolbox.bonecp.BoneCPDataSource
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration, PropertySources}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = Array("nl.haploid.octowight.sample.repository"))
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySources(value = Array())
class PostgresConfiguration {
  private final val log: Logger = LoggerFactory.getLogger(getClass)

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

  @Bean def entityManagerFactory(dataSource: DataSource) = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(true)
    vendorAdapter.setShowSql(true)
    val factory = new LocalContainerEntityManagerFactoryBean
    factory.setJpaVendorAdapter(vendorAdapter)
    factory.setPackagesToScan("nl.haploid.octowight.sample.repository")
    factory.setDataSource(dataSource)
    factory.afterPropertiesSet()
    factory.getObject
  }

  @Bean def transactionManager(entityManagerFactory: EntityManagerFactory) = {
    val manager = new JpaTransactionManager
    manager.setEntityManagerFactory(entityManagerFactory)
    manager
  }
}
