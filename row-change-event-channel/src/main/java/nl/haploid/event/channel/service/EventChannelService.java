package nl.haploid.event.channel.service;

import nl.haploid.event.channel.repository.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventChannelService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RowChangeEventRepository repository;

    @Scheduled(fixedRate = 500)
    @Transactional
    public void queueRowChangeEvents() {
        log.debug("Queue row change events");
        List<RowChangeEvent> events = repository.findAll();
        log.debug(String.format("Found %d row change events", events.size()));
        // TODO: write to queue
        repository.delete(events);
    }
}
