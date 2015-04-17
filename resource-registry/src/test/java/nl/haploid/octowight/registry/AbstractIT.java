package nl.haploid.octowight.registry;

import nl.haploid.octowight.registry.configuration.ResourceRegistryConfiguration;
import nl.haploid.octowight.registry.configuration.TestConfiguration;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, ResourceRegistryConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceDmoRepository repository;

	@Before
	public void setup() {
		repository.deleteAllInBatch();
	}
}
