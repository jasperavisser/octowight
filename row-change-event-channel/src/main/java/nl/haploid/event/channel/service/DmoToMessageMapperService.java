package nl.haploid.event.channel.service;

import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DmoToMessageMapperService {

	public RowChangeEvent map(final RowChangeEventDmo eventDmo) {
		final RowChangeEvent event = new RowChangeEvent();
		event.setId(eventDmo.getId());
		event.setRowId(eventDmo.getRowId());
		event.setTableName(eventDmo.getTableName());
		return event;
	}

	public List<RowChangeEvent> map(final Collection<RowChangeEventDmo> eventDmos) {
		return eventDmos.stream()
				.map(this::map)
				.collect(Collectors.toList());
	}
}
