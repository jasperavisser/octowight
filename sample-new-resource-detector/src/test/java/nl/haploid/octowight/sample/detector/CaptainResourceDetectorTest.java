package nl.haploid.octowight.sample.detector;

import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;
import nl.haploid.octowight.registry.data.ResourceRootFactory;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.RoleDmo;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CaptainResourceDetectorTest {

    @Tested
    private CaptainResourceDetector detector;

    @Injectable
    private RoleDmoRepository roleDmoRepository;

    @Injectable
    private ResourceRootFactory resourceRootFactory;

    @Test
    public void testGetAtomTypes() throws Exception {
        assertEquals(1, detector.getAtomTypes().size());
    }

    @Test
    public void testDetect() throws Exception {
        final AtomChangeEvent event1 = TestData.atomChangeEvent();
        final AtomChangeEvent event2 = TestData.atomChangeEvent();
        final AtomChangeEvent event3 = TestData.atomChangeEvent();
        final List<AtomChangeEvent> events = Arrays.asList(event1, event2, event3);
        final Long id1 = event1.getAtomId();
        final Long id2 = event2.getAtomId();
        final RoleDmo roleDmo1 = TestData.roleDmo(id1);
        final RoleDmo roleDmo2 = TestData.roleDmo(id2);
        final HashMap<Long, RoleDmo> rolesById = new HashMap<Long, RoleDmo>() {{
            put(id1, roleDmo1);
            put(id2, roleDmo2);
        }};
        final ResourceRoot resourceRoot = TestData.resourceRoot(id2);
        final List<ResourceRoot> expectedResourceRoots = Collections.singletonList(resourceRoot);
        new StrictExpectations(detector) {{
            detector.getRolesById(events);
            times = 1;
            result = rolesById;
            detector.isCaptain(roleDmo1);
            times = 1;
            result = false;
            detector.isCaptain(roleDmo2);
            times = 1;
            result = true;
            resourceRootFactory.fromAtomChangeEvent(event2, CaptainResourceDetector.RESOURCE_TYPE);
            times = 1;
            result = resourceRoot;
        }};
        final List<ResourceRoot> actualResourceRoots = detector.detect(events);
        assertEquals(expectedResourceRoots, actualResourceRoots);
    }

    @Test
    public void testGetRolesById() throws Exception {
        final AtomChangeEvent event1 = TestData.atomChangeEvent();
        final AtomChangeEvent event2 = TestData.atomChangeEvent();
        final List<AtomChangeEvent> events = Arrays.asList(event1, event2);
        final Long id1 = event1.getAtomId();
        final Long id2 = event2.getAtomId();
        final RoleDmo roleDmo1 = TestData.roleDmo(id1);
        final RoleDmo roleDmo2 = TestData.roleDmo(id2);
        new StrictExpectations() {{
            roleDmoRepository.findAll(Arrays.asList(id1, id2));
            times = 1;
            result = Arrays.asList(roleDmo1, roleDmo2);
        }};
        final Map<Long, RoleDmo> rolesById = detector.getRolesById(events);
        assertEquals(roleDmo1, rolesById.get(id1));
        assertEquals(roleDmo2, rolesById.get(id2));
    }

    @Test
    public void testIsCaptain(final @Mocked PersonDmo personDmo) {
        final RoleDmo roleDmo1 = TestData.roleDmo(personDmo, "harpooner");
        final RoleDmo roleDmo2 = TestData.roleDmo(personDmo, CaptainResourceDetector.ROLE_TYPE);
        assertFalse(detector.isCaptain(roleDmo1));
        assertTrue(detector.isCaptain(roleDmo2));
    }
}
