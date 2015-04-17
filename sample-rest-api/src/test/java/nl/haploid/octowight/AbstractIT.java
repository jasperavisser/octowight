package nl.haploid.octowight;

import nl.haploid.octowight.configuration.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
// TODO: @ComponentScan on TestConfiguration causes it to find all other @Configuration classes too =(
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {
}
