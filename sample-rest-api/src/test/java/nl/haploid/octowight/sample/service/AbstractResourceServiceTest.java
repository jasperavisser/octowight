package nl.haploid.octowight.sample.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.data.*;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.data.ResourceFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractResourceServiceTest {

	private abstract class MockModel implements Model {
	}

	private abstract class MockResource extends Resource<MockModel> {
	}

	@Tested
	private AbstractResourceService<MockModel, MockResource> service;

	@Injectable
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Injectable
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Injectable
	private ResourceRootFactory resourceRootFactory;

	@Injectable
	private ResourceElementDmoFactory resourceElementDmoFactory;

	@Injectable
	private ResourceModelDocumentFactory resourceModelDocumentFactory;

	@Injectable
	private ResourceModelDmoRepository resourceModelDmoRepository;

	@Injectable
	private ModelSerializer<MockModel> modelSerializer;

	@Injectable
	private ResourceFactory<MockResource> resourceFactory;

	@Test
	public void testGetResourceRoot() {
		final long resourceId = TestData.nextLong();
		final String resourceType = TestData.nextString();
		final ResourceRootDmo resourceRootDmo = TestData.resourceRootDmo();
		final ResourceRoot expectedResourceRoot = TestData.resourceRoot();
		new StrictExpectations() {{
			resourceRootDmoRepository.findByResourceTypeAndResourceId(resourceType, resourceId);
			times = 1;
			result = resourceRootDmo;
			resourceRootFactory.fromResourceDmo(resourceRootDmo);
			times = 1;
			result = expectedResourceRoot;
		}};
		final ResourceRoot actualResourceRoot = service.getResourceRoot(resourceType, resourceId);
		assertEquals(expectedResourceRoot, actualResourceRoot);
	}

	@Test
	public void testSaveModel() {

	}

	@Test
	public void testSaveResourceElements() {

	}

	@Test
	public void testSaveResourceElement() {

	}
}
