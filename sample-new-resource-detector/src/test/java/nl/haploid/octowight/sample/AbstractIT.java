package nl.haploid.octowight.sample;

import nl.haploid.octowight.configuration.KafkaConfiguration;
import nl.haploid.octowight.registry.configuration.ResourceRegistryConfiguration;
import nl.haploid.octowight.sample.configuration.SamplePostgresConfiguration;
import nl.haploid.octowight.sample.configuration.TestConfiguration;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, KafkaConfiguration.class, ResourceRegistryConfiguration.class,
				SamplePostgresConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonDmoRepository repository;

	@Before
	public void setup() {
		repository.deleteAllInBatch();
	}
}

