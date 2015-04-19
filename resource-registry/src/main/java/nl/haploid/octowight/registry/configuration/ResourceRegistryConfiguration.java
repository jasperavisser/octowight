package nl.haploid.octowight.registry.configuration;

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
@EnableJpaRepositories(basePackages = {"nl.haploid.octowight.registry.repository"},
		entityManagerFactoryRef = "registryEntityManagerFactory",
		transactionManagerRef = "registryTransactionManager")
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySources(value = {})
public class ResourceRegistryConfiguration {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Value("${octowight.registry.postgres.hostname}")
	private String hostname;

	@Value("${octowight.registry.postgres.port}")
	private int port;

	@Value("${octowight.registry.postgres.username}")
	private String username;

	@Value("${octowight.registry.postgres.database}")
	private String database;

	@Bean(name = "registryDataSource")
	public DataSource dataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass("org.postgresql.Driver");
		final String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", hostname, port, database);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(username);
		log.debug(String.format("Will connect to %s as %s", jdbcUrl, username));
		return dataSource;
	}

	@Bean(name = "registryEntityManagerFactory")
	public EntityManagerFactory entityManagerFactory(final DataSource registryDataSource) {
		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("nl.haploid.octowight.registry.repository");
		factory.setDataSource(registryDataSource);
		factory.afterPropertiesSet();
		return factory.getObject();
	}

	@Bean(name = "registryTransactionManager")
	public PlatformTransactionManager transactionManager(final EntityManagerFactory registryEntityManagerFactory) {
		final JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(registryEntityManagerFactory);
		return manager;
	}
}
