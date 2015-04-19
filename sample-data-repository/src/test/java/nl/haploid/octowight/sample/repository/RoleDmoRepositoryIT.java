package nl.haploid.octowight.sample.repository;

import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoleDmoRepositoryIT extends AbstractIT {

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	@Test
	@Transactional
	public void testFindAll() {
		final PersonDmo personDmo = personDmoRepository.saveAndFlush(TestData.personDmo());
		final RoleDmo roleDmo = TestData.roleDmo(personDmo, "cleaner");
		roleDmoRepository.saveAndFlush(roleDmo);
		final List<RoleDmo> roleDmos = roleDmoRepository.findAll();
		assertEquals(1, roleDmos.size());
		assertEquals(personDmo.getName(), roleDmos.get(0).getPerson().getName());
	}
}
