package nl.haploid.resource.detector;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.JsonMapper;
import nl.haploid.resource.detector.service.EventConsumerService;
import nl.haploid.resource.detector.service.ResourceDetectorsService;
import nl.haploid.resource.detector.service.ResourceProducerService;
import nl.haploid.resource.detector.service.ResourceRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
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
	private ResourceRegistryService registryService;

	@Autowired
	private ResourceProducerService producerService;

	@Autowired
	private JsonMapper jsonMapper;

	public static void main(final String[] args) {
		SpringApplication.run(App.class);
	}

	// TODO: unit test/IT
	@Scheduled(fixedRate = 500)
	public void poll() {
		consumerService.consumeMessages(batchSize)
				.map(message -> jsonMapper.parse(message, AtomChangeEvent.class))
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomType))
				.entrySet().stream()
				.map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.filter(registryService::isNewResource)
				.map(registryService::registerNewResource)
				.map(producerService::publishResourceDescriptor)
				.collect(Collectors.toList()).stream()
				.map(producerService::resolveFuture)
				.collect(Collectors.toList());
		consumerService.commit();
	}
}
