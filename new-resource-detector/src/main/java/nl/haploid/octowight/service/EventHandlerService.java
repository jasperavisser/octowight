package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
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
	private EventConsumerService eventConsumerService;

	@Autowired
	private ResourceDetectorsService resourceDetectorsService;

	@Autowired
	private ResourceRegistryService resourceRegistryService;

	@Autowired
	private DirtyResourceProducerService dirtyResourceProducerService;

	@Autowired
	private JsonMapper jsonMapper;

	public long detectNewResources(final int batchSize) {
		final Set<Map.Entry<AtomGroup, List<AtomChangeEvent>>> events = eventConsumerService.consumeMessages(batchSize)
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomGroup))
				.entrySet();
		log.debug(String.format("Consumed %d events", events.size()));
		final long count = events.stream()
				.map(entry -> resourceDetectorsService.detectResources(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.filter(resourceRegistryService::isNewResource)
				.map(resourceRegistryService::saveResource)
				.map(dirtyResourceProducerService::sendDirtyResource)
				.collect(Collectors.toList()).stream()
				.map(dirtyResourceProducerService::resolveFuture)
				.count();
		eventConsumerService.commit();
		return count;
	}

	// TODO: test
	public long detectDirtyResources(final int batchSize) {
		final Set<Map.Entry<AtomGroup, List<AtomChangeEvent>>> events = eventConsumerService.consumeMessages(batchSize)
				.collect(Collectors.groupingBy(AtomChangeEvent::getAtomGroup))
				.entrySet();
		log.debug(String.format("Consumed %d events", events.size()));
		final long count = events.stream()
				.map(entry -> resourceRegistryService.markResourcesDirty(entry.getKey(), entry.getValue()))
				.flatMap(Collection::stream)
				.map(dirtyResourceProducerService::sendDirtyResource)
				.collect(Collectors.toList()).stream()
				.map(dirtyResourceProducerService::resolveFuture)
				.count();
		eventConsumerService.commit();
		return count;
	}
}
