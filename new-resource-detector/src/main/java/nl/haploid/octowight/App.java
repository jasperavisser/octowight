package nl.haploid.octowight;

import nl.haploid.octowight.service.EventHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

@ComponentScan
@EnableAutoConfiguration
public class App { // TODO: this library doesn't need an App(Configuration)

	private static final int POLLING_INTERVAL_MS = 500;

	@Value("${kafka.batch.size:100}")
	private int batchSize;

	@Autowired
	private EventHandlerService service;

	public static void main(final String[] args) {
		SpringApplication.run(App.class);
	}

	@Scheduled(fixedRate = POLLING_INTERVAL_MS)
	public void poll() {
		service.handleEvents(batchSize);
	}
}
