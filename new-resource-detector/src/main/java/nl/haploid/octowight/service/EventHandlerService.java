package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.registry.service.ResourceRegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventHandlerService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EventConsumerService consumerService;

	@Autowired
	private ResourceDetectorsService detectorsService;

	@Autowired
	private ResourceRegistryService registryService;

	@Autowired
	private DirtyResourceProducerService producerService;

	@Autowired
	private JsonMapper jsonMapper;

	public long handleEvents(final int batchSize) {
		final Set<Map.Entry<String, List<AtomChangeEvent>>> events = consumerService.consumeMessages(batchSize)
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomType))
				.entrySet();
		// TODO: where are my events??
		log.debug(String.format("Consumed %d events", events.size()));
		final long count = events.stream()
				.map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.filter(registryService::isNewResource)
				.map(registryService::saveResource)
				.map(producerService::sendDirtyResource)
				.collect(Collectors.toList()).stream()
				.map(producerService::resolveFuture)
				.count();
		consumerService.commit();
		return count;
	}
}
