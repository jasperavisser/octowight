package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EventHandlerService {

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

	public long handleEvents(final int batchSize) {
		final long count = consumerService.consumeMessages(batchSize)
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
				.count();
		consumerService.commit();
		return count;
	}
}
