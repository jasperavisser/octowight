package nl.haploid.octowight.sample.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import nl.haploid.octowight.sample.repository.RoleDmo;
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
	public void testGetRolesById() {
		final PersonDmo personDmo = personDmoRepository.saveAndFlush(TestData.personDmo());
		final RoleDmo roleDmo1 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "first mate"));
		roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "harpooner"));
		final AtomChangeEvent event1 = TestData.atomChangeEvent(roleDmo1.getId());
		final List<AtomChangeEvent> events = Collections.singletonList(event1);
		final Map<Long, RoleDmo> dmosById = detector.getRolesById(events);
		assertEquals(1, dmosById.size());
		assertEquals(roleDmo1, dmosById.get(roleDmo1.getId()));
	}

	@Test
	public void testDetect() throws Exception {
		final PersonDmo personDmo = personDmoRepository.saveAndFlush(TestData.personDmo());
		final RoleDmo dmo1 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, CaptainResourceDetector.ROLE_TYPE));
		final RoleDmo dmo2 = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, "deckhand"));
		entityManager.clear();
		final AtomChangeEvent event1 = TestData.atomChangeEvent(dmo1.getId());
		final AtomChangeEvent event2 = TestData.atomChangeEvent(dmo2.getId());
		final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
		final List<ResourceRoot> actualResourceRoots = detector.detect(events);
		assertEquals(1, actualResourceRoots.size());
		assertEquals(dmo1.getId(), actualResourceRoots.get(0).getAtomId());
	}
}
