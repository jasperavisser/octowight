package nl.haploid.octowight;

import nl.haploid.octowight.configuration.SamplePostgresConfiguration;
import nl.haploid.octowight.configuration.TestConfiguration;
import nl.haploid.octowight.repository.BookDmoRepository;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, SamplePostgresConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private BookDmoRepository repository;

	@Before
	public void setup() {
		repository.deleteAllInBatch();
	}
}

