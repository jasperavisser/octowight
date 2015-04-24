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
import nl.haploid.octowight.sample.data.CaptainResource;
import nl.haploid.octowight.sample.data.CaptainResourceFactory;
import nl.haploid.octowight.sample.data.CaptainModel;
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
    private CaptainResourceFactory captainResourceFactory;

    @Injectable
    private ResourceModelDmoFactory resourceModelDmoFactory;

    @Injectable
    private ResourceModelDmoRepository resourceModelDmoRepository;

    @Injectable
    private ModelSerializer<CaptainModel> modelSerializer;

    @Test
    public void testGetCaptain(final @Mocked CaptainResource captainResource) {
        final Long resourceId = TestData.nextLong();
        final ResourceRoot resourceRoot = TestData.resourceRoot(resourceId);
        final CaptainModel expectedCaptainModel = TestData.captainModel();
        final ResourceModelDmo resourceModelDmo = TestData.resourceModelDmo();
        final String body = TestData.nextString();
        new StrictExpectations(service) {{
            service.getResourceRoot(CaptainResource.RESOURCE_TYPE, resourceId);
            times = 1;
            result = resourceRoot;
            captainResourceFactory.fromResourceRoot(resourceRoot);
            times = 1;
            result = captainResource;
            service.saveResourceElements(captainResource);
            times = 1;
            captainResource.getModel();
            times = 1;
            result = expectedCaptainModel;
            modelSerializer.toString(expectedCaptainModel);
            times = 1;
            result = body;
            service.saveModel(captainResource, body);
            times = 1;
        }};
        final CaptainModel actualCaptainModel = service.getCaptain(resourceId);
        assertEquals(expectedCaptainModel, actualCaptainModel);
    }
}
