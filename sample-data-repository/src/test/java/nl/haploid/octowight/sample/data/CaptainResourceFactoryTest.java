package nl.haploid.octowight.sample.data;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaptainResourceFactoryTest {

	@Tested
	private CaptainResourceFactory captainResourceFactory;

	@Injectable
	private PersonDmoRepository personDmoRepository;

	@Test
	public void testFromResourceRoot() {
		final ResourceRoot resourceRoot = TestData.resourceRoot();
		final PersonDmo personDmo = TestData.personDmo();
		new StrictExpectations() {{
			personDmoRepository.findOne(resourceRoot.getAtomId());
			times = 1;
			result = personDmo;
		}};
		final CaptainResource captainResource = captainResourceFactory.fromResourceRoot(resourceRoot);
		assertEquals(resourceRoot.getResourceId(), captainResource.getId());
		assertEquals(1, captainResource.getAtoms().size());
		assertEquals(resourceRoot.getVersion(), captainResource.getVersion());
	}
}
