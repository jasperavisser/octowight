package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
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
	private ResourceRegistryService resourceRegistryService;

	@Autowired
	private DirtyResourceProducerService dirtyResourceProducerService;

	// TODO: test
	public long detectDirtyResources(final int batchSize) {
		log.debug(String.format("Poll for atom change events on %s", eventConsumerService.getTopic()));
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
		log.debug(String.format("Marked %d resources as dirty", count));
		eventConsumerService.commit();
		return count;
	}
}
