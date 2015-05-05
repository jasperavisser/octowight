package nl.haploid.octowight.sample;

import nl.haploid.octowight.configuration.KafkaConfiguration;
import nl.haploid.octowight.sample.configuration.PostgresConfiguration;
import nl.haploid.octowight.sample.configuration.TestConfiguration;
import nl.haploid.octowight.sample.repository.AtomChangeEventDmoRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, KafkaConfiguration.class, PostgresConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private AtomChangeEventDmoRepository atomChangeEventDmoRepository;

	@Before
	public void setup() {
		atomChangeEventDmoRepository.deleteAllInBatch();
	}
}

