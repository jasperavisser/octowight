package nl.haploid.octowight.sample.repository;

import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PersonDmoRepositoryIT extends AbstractIT {

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	@Transactional
	public void testFindAll() {
		final PersonDmo personDmo = personDmoRepository.saveAndFlush(TestData.personDmo());
		roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "cleaner"));
		roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "supervisor"));
		entityManager.clear();
		final List<PersonDmo> personDmos = personDmoRepository.findAll();
		assertEquals(1, personDmos.size());
		assertEquals(2, personDmos.get(0).getRoles().size());
	}
}
