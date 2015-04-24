package nl.haploid.octowight.sample.service;

import mockit.Injectable;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.registry.data.Model;
import nl.haploid.octowight.registry.data.ModelSerializer;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.registry.repository.*;
import nl.haploid.octowight.sample.TestData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AbstractResourceServiceTest {

    @Tested
    private AbstractResourceService<Model> service;

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
    private ModelSerializer<Model> modelSerializer;

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