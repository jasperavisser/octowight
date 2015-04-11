package nl.haploid.event.channel.service;

import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventDmo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DmoMessageMapperService {

    public RowChangeEvent map(final RowChangeEventDmo eventDmo) {
        final RowChangeEvent event = new RowChangeEvent();
        event.setId(eventDmo.getId());
        event.setRowId(eventDmo.getRowId());
        event.setTableName(eventDmo.getTableName());
        return event;
    }

    // TODO: would be much shorter in java8 :)
    public List<RowChangeEvent> map(final Collection<RowChangeEventDmo> eventDmos) {
        final List<RowChangeEvent> events = new ArrayList<RowChangeEvent>();
        for (RowChangeEventDmo eventDmo : eventDmos) {
            events.add(map(eventDmo));
        }
        return events;
    }
}
