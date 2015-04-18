package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.sample.repository.AtomChangeEventDmo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AtomChangeEventFactory {

	public AtomChangeEvent fromAtomChangeEventDmo(final AtomChangeEventDmo eventDmo) {
		final AtomChangeEvent event = new AtomChangeEvent();
		event.setId(eventDmo.getId());
		event.setAtomId(eventDmo.getAtomId());
		event.setAtomLocus(eventDmo.getAtomLocus());
		event.setAtomType(eventDmo.getAtomType());
		return event;
	}

	public List<AtomChangeEvent> fromAtomChangeEventDmos(final Collection<AtomChangeEventDmo> eventDmos) {
		return eventDmos.stream()
				.map(this::fromAtomChangeEventDmo)
				.collect(Collectors.toList());
	}
}
