package nl.haploid.octowight;

import nl.haploid.octowight.configuration.KafkaConfiguration;
import nl.haploid.octowight.configuration.TestConfiguration;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(
		classes = {TestConfiguration.class, KafkaConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractJUnit4SpringContextTests {
}
