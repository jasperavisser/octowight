package nl.haploid.octowight;

import nl.haploid.octowight.service.EventConsumerService;
import nl.haploid.octowight.service.ResourceDetectorsService;
import nl.haploid.octowight.service.DirtyResourceProducerService;
import nl.haploid.octowight.service.ResourceCoreAtomRegistryService;
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

	private static final int POLLING_INTERVAL_MS = 500;

	@Value("${kafka.batch.size:100}")
	private int batchSize;

	@Autowired
	private EventConsumerService consumerService;

	@Autowired
	private ResourceDetectorsService detectorsService;

	@Autowired
	private ResourceCoreAtomRegistryService registryService;

	@Autowired
	private DirtyResourceProducerService producerService;

	@Autowired
	private JsonMapper jsonMapper;

	public static void main(final String[] args) {
		SpringApplication.run(App.class);
	}

	// TODO: put this in a service
	@Scheduled(fixedRate = POLLING_INTERVAL_MS)
	public void poll() {
		consumerService.consumeMessages(batchSize)
				.map(message -> jsonMapper.parse(message, AtomChangeEvent.class))
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomType))
				.entrySet().stream()
				.map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.filter(registryService::isNewResource)
				.map(registryService::putNewResource)
				.map(producerService::sendDirtyResource)
				.collect(Collectors.toList()).stream()
				.map(producerService::resolveFuture)
				.collect(Collectors.toList());
		consumerService.commit();
	}
}
