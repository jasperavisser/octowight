package nl.haploid.octowight.sample.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.data.Captain;
import nl.haploid.octowight.sample.data.CaptainFactory;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CaptainServiceTest {

	@Tested
	private CaptainService service;

	@Injectable
	private ResourceDmoRepository resourceDmoRepository;

	@Injectable
	private PersonDmoRepository personDmoRepository;

	@Injectable
	private CaptainFactory captainFactory;

	@Test
	public void testGetCaptain() {
		final Long resourceId = 123l;
		final ResourceDmo resourceDmo = TestData.resourceDmo(resourceId);
		final PersonDmo personDmo = TestData.personDmo();
		final Captain captain = TestData.captain();
		new StrictExpectations() {{
			resourceDmoRepository.findByResourceTypeAndResourceId(Captain.RESOURCE_TYPE, resourceId);
			times = 1;
			result = resourceDmo;
			personDmoRepository.findOne(resourceDmo.getAtomId());
			times = 1;
			result = personDmo;
			captainFactory.fromPersonDmo(personDmo, resourceId);
			times = 1;
			result = captain;
		}};
		final Captain actualCaptain = service.getCaptain(resourceId);
		assertEquals(resourceId, actualCaptain.getId());
	}

	@Test
	public void testGetCaptains() {
		final long personId1 = 111l;
		final long personId2 = 222l;
		final Set<Long> personIds = new HashSet<>(Arrays.asList(personId1, personId2));
		final long resourceId1 = 123l;
		final long resourceId2 = 456l;
		final ResourceDmo resourceDmo1 = TestData.resourceDmo(resourceId1);
		final ResourceDmo resourceDmo2 = TestData.resourceDmo(resourceId2);
		final List<ResourceDmo> resourceDmos = Arrays.asList(resourceDmo1, resourceDmo2);
		resourceDmo1.setAtomId(personId1);
		resourceDmo2.setAtomId(personId2);
		final PersonDmo personDmo1 = TestData.personDmo(personId1);
		final PersonDmo personDmo2 = TestData.personDmo(personId2);
		final List<PersonDmo> personDmos = Arrays.asList(personDmo1, personDmo2);
		final Captain captain1 = TestData.captain();
		final Captain captain2 = TestData.captain();
		final List<Captain> expectedCaptains = Arrays.asList(captain1, captain2);
		new StrictExpectations() {{
			resourceDmoRepository.findByResourceType(Captain.RESOURCE_TYPE);
			times = 1;
			result = resourceDmos;
			personDmoRepository.findAll(personIds);
			times = 1;
			result = personDmos;
			captainFactory.fromPersonDmo(personDmo1, resourceId1);
			times = 1;
			result = captain1;
			captainFactory.fromPersonDmo(personDmo2, resourceId2);
			times = 1;
			result = captain2;
		}};
		final List<Captain> actualCaptains = service.getCaptains();
		assertEquals(expectedCaptains, actualCaptains);
	}
}
