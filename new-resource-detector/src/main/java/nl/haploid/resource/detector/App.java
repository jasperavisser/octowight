package nl.haploid.resource.detector;

import nl.haploid.event.JsonMapper;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.resource.detector.service.EventConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Collectors;

@ComponentScan
@EnableAutoConfiguration
public class App {

    @Value("${kafka.batch.size:100}")
    private int batchSize;

    @Autowired
    private EventConsumerService consumerService;

    @Autowired
    private JsonMapper jsonMapper;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Scheduled(fixedRate = 500)
    public void poll() {
        final List<String> messages = consumerService.consumeMultipleMessages(batchSize);
        final List<RowChangeEvent> events = messages.stream()
                .map(m -> jsonMapper.parse(m, RowChangeEvent.class))
                .collect(Collectors.toList());
        // TODO: group events by table
        // TODO: detect resources
        // TODO: commit offsets
    }
}
