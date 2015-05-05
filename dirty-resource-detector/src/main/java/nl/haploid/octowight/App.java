package nl.haploid.octowight;

import nl.haploid.octowight.service.EventHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

@EnableAutoConfiguration
@ComponentScan(basePackages = "nl.haploid.octowight")
public class App {

	private static final int POLLING_INTERVAL_MS = 1000;

	public static void main(final String[] args) {
		SpringApplication.run(App.class);
	}

	@Value("${octowight.kafka.batch.size}")
	private int batchSize;

	@Autowired
	private EventHandlerService eventHandlerService;

	@Scheduled(fixedRate = POLLING_INTERVAL_MS)
	public void poll() {
		eventHandlerService.detectDirtyResources(batchSize);
	}
}
