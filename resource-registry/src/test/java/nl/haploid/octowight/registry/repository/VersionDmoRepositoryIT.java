package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

public class VersionDmoRepositoryIT extends AbstractIT {

	@Autowired
	private VersionDmoRepository versionDmoRepository;

	@Test
	public void testFindNext() {
		final VersionDmo versionDmo = versionDmoRepository.findNext();
		assertNotNull(versionDmo);
		assertNotNull(versionDmo.getVersion());
	}
}
