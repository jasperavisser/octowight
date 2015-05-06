package nl.haploid.octowight.sample.data;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaptainResourceFactoryTest {

	@Tested
	private CaptainResourceFactory captainResourceFactory;

	@Injectable
	private RoleDmoRepository roleDmoRepository;

	@Test
	public void testFromResourceRoot(final @Mocked RoleDmo roleDmo) {
		final ResourceRoot resourceRoot = TestData.resourceRoot();
		final PersonDmo personDmo = TestData.personDmo();
		new StrictExpectations() {{
			roleDmoRepository.findOne(resourceRoot.getAtomId());
			times = 1;
			result = roleDmo;
			roleDmo.setAtomOrigin(resourceRoot.getAtomOrigin());
			times = 1;
			roleDmo.getPerson();
			times = 1;
			result = personDmo;
		}};
		final CaptainResource captainResource = captainResourceFactory.fromResourceRoot(resourceRoot);
		assertEquals(resourceRoot.getResourceId(), captainResource.getId());
		assertEquals(2, captainResource.getAtoms().size());
		assertEquals(resourceRoot.getVersion(), captainResource.getVersion());
	}
}
