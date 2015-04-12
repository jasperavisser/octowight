package nl.haploid.event.channel;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.sql.SQLException;

@ContextConfiguration(
		classes = {TestConfiguration.class, KafkaConfiguration.class, PostgresConfiguration.class},
		loader = AnnotationConfigContextLoader.class)
public abstract class AbstractIT extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private DataSource dataSource;

	private static boolean isSetup = false;

	public void initializeDatabase() throws SQLException {
		final ClassPathResource resource = new ClassPathResource("/initialize-database.sql", getClass());
		ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
	}

	@Before
	public void setup() throws SQLException {
		if (!isSetup) {
			initializeDatabase();
			isSetup = true;
		}
	}
}

