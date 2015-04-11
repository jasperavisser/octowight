package nl.haploid.resource.detector;

import nl.haploid.resource.detector.service.EventConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@ComponentScan
@EnableAutoConfiguration
public class App {

    @Value("${kafka.batch.size:100}")
    private int batchSize;

    @Autowired
    private EventConsumerService consumerService;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Scheduled(fixedRate = 500)
    public void poll() {
        final List<String> messages = consumerService.consumeMultipleMessages(batchSize);
        // TODO: detect resources
        // TODO: commit offsets
        // TODO: we expect to have only 1 consumer at the same time
        // TODO: but we want to be able to reset it (shutdown, get new one) during IT
    }
}
