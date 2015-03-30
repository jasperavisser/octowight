package nl.haploid.event.channel

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import com.jolbox.bonecp.BoneCPDataSource
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import javax.persistence.EntityManagerFactory
import org.springframework.context.annotation.PropertySources
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Value
import javax.sql.DataSource
import org.springframework.context.annotation.PropertySource
import org.slf4j.LoggerFactory
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories(basePackages = Array("nl.haploid.event.channel.repository"))
@EnableTransactionManagement
@PropertySources(value = Array(new PropertySource(value = Array("file:./override.properties"), ignoreResourceNotFound = true)))
class PostgresConfiguration {

	val log = LoggerFactory.getLogger(getClass)

	@Value("${postgres.hostname}")
	var postgresHostname: String = _

	@Value("${postgres.port}")
	var postgresPort: Long = _

	@Value("${postgres.username}")
	var postgresUsername: String = _

	@Value("${postgres.database}")
	var postgresDatabase: String = _

	@Bean
	def dataSource: DataSource = {
		val dataSource = new BoneCPDataSource
		dataSource.setDriverClass("org.postgresql.Driver")
		val jdbcUrl = "jdbc:postgresql://%s:%d/%s".format(postgresHostname, postgresPort, postgresDatabase)
		dataSource.setJdbcUrl(jdbcUrl)
		dataSource.setUsername(postgresUsername)
		dataSource.setPassword(postgresUsername)
		log.debug("Will connect to %s as %s".format(jdbcUrl, postgresUsername))
		dataSource
	}

	@Bean
	def entityManagerFactory(dataSource: DataSource): EntityManagerFactory = {
		val vendorAdapter = new HibernateJpaVendorAdapter
		vendorAdapter.setGenerateDdl(true)
		vendorAdapter.setShowSql(true)
		val factory = new LocalContainerEntityManagerFactoryBean
		factory.setJpaVendorAdapter(vendorAdapter)
		factory.setPackagesToScan("nl.haploid.event.channel")
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
