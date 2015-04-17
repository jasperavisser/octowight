package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.JsonMapper;
import nl.haploid.octowight.registry.service.ResourceRegistryService;
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
	private ResourceRegistryService registryService;

	@Autowired
	private DirtyResourceProducerService producerService;

	@Autowired
	private JsonMapper jsonMapper;

	public long handleEvents(final int batchSize) {
		final long count = consumerService.consumeMessages(batchSize)
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomType))
				.entrySet().stream()
				.map(entry -> detectorsService.detectResources(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.filter(registryService::isNewResource)
						/**
						 * TODO: alternatively
						 * assign resource id
						 * send to kafka
						 * then register resource in a separate app (maybe api)
						 * CON: it is possible to assign multiple resource ids to the same atom
						 */
				.map(registryService::saveResource)
				.map(producerService::sendDirtyResource)
				.collect(Collectors.toList()).stream()
				.map(producerService::resolveFuture)
				.count();
		consumerService.commit();
		return count;
	}
}
