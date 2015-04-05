package nl.haploid.event.channel

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import com.jolbox.bonecp.BoneCPDataSource
import grizzled.slf4j.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.{Bean, Configuration, PropertySource, PropertySources}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = Array("nl.haploid.event.channel.repository"))
@EnableTransactionManagement
@PropertySources(value = Array(new PropertySource(value = Array("file:./override.properties"), ignoreResourceNotFound = true)))
class PostgresConfiguration extends Logging {

  @Value("${postgres.hostname}") val _hostname: String = null
  @Value("${postgres.port}") val port: Int = 5432
  @Value("${postgres.username}") val username: String = null
  @Value("${postgres.database}") val database: String = null

  def hostname = _hostname

  @Bean
  def dataSource: DataSource = {
    val dataSource = new BoneCPDataSource
    dataSource.setDriverClass("org.postgresql.Driver")
    val jdbcUrl = "jdbc:postgresql://%s:%d/%s".format(hostname, port, database)
    dataSource.setJdbcUrl(jdbcUrl)
    dataSource.setUsername(username)
    dataSource.setPassword(username)
    debug("Will connect to %s as %s".format(jdbcUrl, username))
    dataSource
  }

  @Bean
  def entityManagerFactory(dataSource: DataSource): EntityManagerFactory = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(true)
    vendorAdapter.setShowSql(true)
    val factory = new LocalContainerEntityManagerFactoryBean
    factory.setJpaVendorAdapter(vendorAdapter)
    factory.setPackagesToScan("nl.haploid.event.channel.repository")
    factory.setDataSource(dataSource)
    factory.afterPropertiesSet
    factory.getObject
  }

  @Bean
  def transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager = {
    val manager = new JpaTransactionManager
    manager.setEntityManagerFactory(entityManagerFactory)
    manager
  }
}
