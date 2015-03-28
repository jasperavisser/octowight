package nl.haploid.adapter

import javax.sql.DataSource

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.integration.annotation.{InboundChannelAdapter, Poller, ServiceActivator}
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.core.MessageSource
import org.springframework.integration.dsl.core.Pollers
import org.springframework.integration.handler.LoggingHandler
import org.springframework.integration.jdbc.JdbcPollingChannelAdapter
import org.springframework.integration.scheduling.PollerMetadata
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.embedded.{EmbeddedDatabaseBuilder, EmbeddedDatabaseType}
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ComponentScan(basePackages = Array("nl.haploid.adapter"))
@EnableIntegration
class AppConfiguration {

  @Bean
  def dataSource: DataSource =
    new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.HSQL)
      .addScript("classpath:create-tables.sql")
      .build

  @Bean
  def transactionManager(dataSource: DataSource): PlatformTransactionManager = {
    val manager = new DataSourceTransactionManager
    manager.setDataSource(dataSource)
    manager
  }

  @Bean(name = Array("rowChangePoller"))
  def rowChangePoller: PollerMetadata = Pollers.fixedDelay(1000).get

  @Bean
  @InboundChannelAdapter(value = "target", poller = Array(new Poller("rowChangePoller")))
  def rowChangeEventMessageSource(dataSource: DataSource): MessageSource[Object] = {
    val adapter = new JdbcPollingChannelAdapter(dataSource, "select * from joke")
    adapter.setUpdateSql("insert into joke values(123)")
    adapter
  }

  @Bean
  @ServiceActivator(inputChannel = "target")
  def foo: LoggingHandler = new LoggingHandler("warn")

  @Bean
  def target: DirectChannel = new DirectChannel
}
