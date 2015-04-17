package nl.haploid.octowight.configuration;

import com.jolbox.bonecp.BoneCPDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"nl.haploid.octowight.repository"})
@EnableTransactionManagement
@PropertySources(value = {})
// TODO: use separate PG for resource registry & example data
public class PostgresConfiguration {

	private final Logger log = LoggerFactory.getLogger(getClass());

	// TODO: @Value("${octowight.postgres.hostname:localhost}")
	@Value("${octowight.postgres.hostname:192.168.59.103}")
	private String hostname;

	@Value("${octowight.postgres.port:5432}")
	private int port;

	@Value("${octowight.postgres.username:postgres}")
	private String username;

	@Value("${octowight.postgres.database:postgres}")
	private String database;

	protected String getHostname() {
		return hostname;
	}

	protected int getPort() {
		return port;
	}

	protected String getUsername() {
		return username;
	}

	protected String getDatabase() {
		return database;
	}

	@Bean
	public DataSource dataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass("org.postgresql.Driver");
		String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", getHostname(), getPort(), getDatabase());
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(getUsername());
		dataSource.setPassword(getUsername());
		log.debug(String.format("Will connect to %s as %s", jdbcUrl, getUsername()));
		return dataSource;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory(final DataSource dataSource) {
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("nl.haploid.octowight.repository");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
		final JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactory);
		return manager;
	}
}
