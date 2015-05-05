package nl.haploid.octowight.sample;

import nl.haploid.octowight.sample.configuration.PostgresConfiguration;
import nl.haploid.octowight.sample.configuration.TestConfiguration;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, PostgresConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	@Before
	public void setup() {
		roleDmoRepository.deleteAllInBatch();
		personDmoRepository.deleteAllInBatch();
	}
}

