package nl.haploid.resource.detector;

import nl.haploid.event.JsonMapper;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.resource.detector.service.EventConsumerService;
import nl.haploid.resource.detector.service.Resource;
import nl.haploid.resource.detector.service.ResourceDetectorsService;
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
    private ResourceDetectorsService detectorsService;

    @Autowired
    private JsonMapper jsonMapper;

    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }

    @Scheduled(fixedRate = 500)
    public void poll() {
        final List<String> messages = consumerService.consumeMultipleMessages(batchSize);
        final List<Resource> resources = messages.stream()
                .map(message -> jsonMapper.parse(message, RowChangeEvent.class))
                .collect(Collectors.groupingBy(event -> event.getTableName()))
                .entrySet().stream()
                .map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
                .flatMap(resourceList -> resourceList.stream())
                .collect(Collectors.toList());

        // TODO: filter existing resources
        // TODO: publish resources
        // TODO: commit offsets
    }
}
