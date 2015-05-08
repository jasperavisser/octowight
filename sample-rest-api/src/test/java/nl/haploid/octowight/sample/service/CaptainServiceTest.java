package nl.haploid.octowight.sample.service;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.data.ModelSerializer;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.data.CaptainModel;
import nl.haploid.octowight.sample.data.CaptainResource;
import nl.haploid.octowight.sample.data.ResourceFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CaptainServiceTest {

	@Tested
	private CaptainService service;

	@Injectable
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Injectable
	private ResourceElementDmoRepository resourceElementDmoRepository;

	@Injectable
	private ResourceRootFactory resourceRootFactory;

	@Injectable
	private ResourceElementDmoFactory resourceElementDmoFactory;

	@Injectable
	private ResourceModelDmoFactory resourceModelDmoFactory;

	@Injectable
	private ResourceModelDmoRepository resourceModelDmoRepository;

	@Injectable
	private ResourceModelIdFactory resourceModelIdFactory;

	@Injectable
	private ModelSerializer<CaptainModel> modelSerializer;

	@Injectable
	private ResourceFactory<CaptainResource> resourceFactory;

	@Test
	public void testGetModel(final @Mocked CaptainResource captainResource) {
		final Long resourceId = TestData.nextLong();
		final String resourceType = CaptainResource.ResourceType();
		final ResourceRoot resourceRoot = TestData.resourceRoot(resourceId);
		final CaptainModel expectedCaptainModel = TestData.captainModel();
		new StrictExpectations(service) {{
			service.getResourceType();
			times = 1;
			result = resourceType;
			service.getResourceRoot(resourceType, resourceId);
			times = 1;
			result = resourceRoot;
			service.getCachedModel(resourceRoot);
			times = 1;
			result = null;
			resourceFactory.fromResourceRoot(resourceRoot);
			times = 1;
			result = captainResource;
			service.saveResourceElements(captainResource);
			times = 1;
			captainResource.getModel();
			times = 1;
			result = expectedCaptainModel;
			service.saveModel(captainResource, expectedCaptainModel);
			times = 1;
		}};
		final CaptainModel actualCaptainModel = service.getModel(resourceId);
		assertEquals(expectedCaptainModel, actualCaptainModel);
	}

	@Test
	public void testGetModelFromCache(final @Mocked CaptainResource captainResource) {
		final Long resourceId = TestData.nextLong();
		final String resourceType = CaptainResource.ResourceType();
		final ResourceRoot resourceRoot = TestData.resourceRoot(resourceId);
		final CaptainModel expectedCaptainModel = TestData.captainModel();
		new StrictExpectations(service) {{
			service.getResourceType();
			times = 1;
			result = resourceType;
			service.getResourceRoot(resourceType, resourceId);
			times = 1;
			result = resourceRoot;
			service.getCachedModel(resourceRoot);
			times = 1;
			result = expectedCaptainModel;
		}};
		final CaptainModel actualCaptainModel = service.getModel(resourceId);
		assertEquals(expectedCaptainModel, actualCaptainModel);
	}
}
