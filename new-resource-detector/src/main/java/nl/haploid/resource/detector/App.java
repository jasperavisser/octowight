package nl.haploid.resource.detector;

import nl.haploid.event.JsonMapper;
import nl.haploid.event.RowChangeEvent;
import nl.haploid.resource.detector.service.EventConsumerService;
import nl.haploid.resource.detector.service.ResourceDescriptor;
import nl.haploid.resource.detector.service.ResourceDetectorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
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
        final List<ResourceDescriptor> resourceDescriptors = consumerService.consumeMultipleMessages(batchSize).stream()
                .map(message -> jsonMapper.parse(message, RowChangeEvent.class))
                .collect(Collectors.groupingBy(RowChangeEvent::getTableName))
                .entrySet().stream()
                .map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        // TODO: filter existing resources (redis, look up tableName + rowId + resourceType)
        // TODO: Assumption: each row represents no more than 1 resource of any given type
        // TODO: Assumption: each row can represent resources of multiple types
        // TODO: publish resources
        // TODO: commit offsets
    }
}
