package nl.haploid.event.channel.service;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.event.channel.repository.AtomChangeEventDmo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmoToMessageMapperService {

	public AtomChangeEvent map(final AtomChangeEventDmo eventDmo) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(eventDmo.getId());
		event.setAtomId(eventDmo.getAtomId());
		event.setAtomLocus(eventDmo.getAtomLocus());
		event.setAtomType(eventDmo.getAtomType());
		return event;
	}

	public List<AtomChangeEvent> map(final Collection<AtomChangeEventDmo> eventDmos) {
		return eventDmos.stream()
				.map(this::map)
				.collect(Collectors.toList());
	}
}
