package nl.haploid.event.channel;

import nl.haploid.event.channel.service.EventChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@ComponentScan
@EnableAutoConfiguration
public class App {

	@Autowired
	private EventChannelService service;

	public static void main(String[] args) {
		SpringApplication.run(App.class);
	}

	@Scheduled(fixedRate = 500)
	public void poll() throws ExecutionException, InterruptedException, IOException {
		service.queueAtomChangeEvents();
	}
}
