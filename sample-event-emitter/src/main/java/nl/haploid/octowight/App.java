package nl.haploid.octowight;

import nl.haploid.octowight.repository.AtomChangeEventDmo;
import nl.haploid.octowight.repository.AtomChangeEventDmoRepository;
import nl.haploid.octowight.service.AtomChangeEventFactory;
import nl.haploid.octowight.service.EventChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ComponentScan
@EnableAutoConfiguration
public class App {

	@Autowired
	private EventChannelService service;

	@Autowired
	private AtomChangeEventDmoRepository repository;

	@Autowired
	private AtomChangeEventFactory eventFactory;

	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}

	@Scheduled(fixedRate = 500)
	@Transactional
	public void poll() {
		final List<AtomChangeEventDmo> eventDmos = repository.findAll();
		final List<AtomChangeEvent> events = eventFactory.fromAtomChangeEventDmos(eventDmos);
		service.sendEvents(events);
		repository.delete(eventDmos);
	}
}
