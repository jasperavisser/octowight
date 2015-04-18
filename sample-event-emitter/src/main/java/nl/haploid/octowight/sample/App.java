package nl.haploid.octowight.sample;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.sample.repository.AtomChangeEventDmo;
import nl.haploid.octowight.sample.repository.AtomChangeEventDmoRepository;
import nl.haploid.octowight.sample.service.AtomChangeEventFactory;
import nl.haploid.octowight.service.EventChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ComponentScan(basePackages = "nl.haploid.octowight")
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
