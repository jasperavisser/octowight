package nl.haploid.octowight.sample;

import nl.haploid.octowight.service.EventConsumerService;
import nl.haploid.octowight.service.EventHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

@ComponentScan(basePackages = "nl.haploid.octowight")
@EnableAutoConfiguration(exclude = {
		DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		JpaRepositoriesAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class App {

	private Logger log = LoggerFactory.getLogger(getClass());

	private static final int POLLING_INTERVAL_MS = 500;

	@Value("${octowight.kafka.batch.size}")
	private int batchSize;

	@Autowired
	private EventConsumerService eventConsumerService;

	@Autowired
	private EventHandlerService service;

	public static void main(final String[] args) {
		SpringApplication.run(App.class);
	}

	// TODO: split up into 2 apps: detectors -> kafka -> registry
	@Scheduled(fixedRate = POLLING_INTERVAL_MS)
	public void poll() {
		log.debug(String.format("Poll for atom change events on %s", eventConsumerService.getTopic()));
		service.handleEvents(batchSize);
	}
}
