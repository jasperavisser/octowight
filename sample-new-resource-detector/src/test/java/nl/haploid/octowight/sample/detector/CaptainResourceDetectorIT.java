package nl.haploid.octowight.sample.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CaptainResourceDetectorIT extends AbstractIT {

	@Autowired
	private CaptainResourceDetector detector;

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	@PersistenceContext(unitName = "sampleEntityManagerFactory")
	private EntityManager entityManager;

	@Test
	public void testGetPersonsById() {
		final PersonDmo dmo1 = personDmoRepository.saveAndFlush(TestData.personDmo());
		final PersonDmo dmo2 = personDmoRepository.saveAndFlush(TestData.personDmo());
		final AtomChangeEvent event1 = TestData.atomChangeEvent(dmo1.getId());
		final List<AtomChangeEvent> events = Collections.singletonList(event1);
		final Map<Long, PersonDmo> dmosById = detector.getPersonsById(events);
		assertEquals(1, dmosById.size());
		assertEquals(dmo1, dmosById.get(dmo1.getId()));
	}

	@Test
	public void testDetect() throws Exception {
		final PersonDmo dmo1 = personDmoRepository.saveAndFlush(TestData.personDmo());
		final PersonDmo dmo2 = personDmoRepository.saveAndFlush(TestData.personDmo());
		roleDmoRepository.saveAndFlush(TestData.roleDmo(dmo1, CaptainResourceDetector.ROLE_TYPE));
		entityManager.clear();
		final AtomChangeEvent event1 = TestData.atomChangeEvent(dmo1.getId());
		final AtomChangeEvent event2 = TestData.atomChangeEvent(dmo2.getId());
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceRoot> actualResourceRoots = detector.detect(events);
		assertEquals(1, actualResourceRoots.size());
		assertEquals(dmo1.getId(), actualResourceRoots.get(0).getAtomId());
	}
}
