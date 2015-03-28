package nl.haploid.jooq

import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.jdbc.datasource.embedded.{EmbeddedDatabaseBuilder, EmbeddedDatabaseType}
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.jooq"))
class AppConfiguration {

  @Bean
  def dataSource: DataSource =
    new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.HSQL)
      .addScript("classpath:create-tables.sql")
      .build

  @Bean
  def entityManagerFactory(dataSource: DataSource): EntityManagerFactory = {
    val vendorAdapter = new HibernateJpaVendorAdapter
    vendorAdapter.setGenerateDdl(true)
    vendorAdapter.setShowSql(true)
    val factory = new LocalContainerEntityManagerFactoryBean
    factory.setJpaVendorAdapter(vendorAdapter)
    factory.setDataSource(dataSource)
    factory.setPackagesToScan("nl.haploid.jooq")
    factory.afterPropertiesSet()
    factory.getObject
  }

  @Bean
  def transactionManager(entityManagerFactory: EntityManagerFactory): PlatformTransactionManager = {
    val manager = new JpaTransactionManager
    manager.setEntityManagerFactory(entityManagerFactory)
    manager
  }
}
