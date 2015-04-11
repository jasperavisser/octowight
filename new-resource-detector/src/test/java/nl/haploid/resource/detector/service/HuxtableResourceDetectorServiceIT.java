package nl.haploid.resource.detector.service;

import nl.haploid.resource.detector.AbstractIT;
import nl.haploid.resource.detector.TestData;
import nl.haploid.resource.detector.repository.Huxtable;
import nl.haploid.resource.detector.repository.HuxtableRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class HuxtableResourceDetectorServiceIT extends AbstractIT {

    @Autowired
    private HuxtableRepository repository;

    @Autowired
    private HuxtableResourceDetectorService service;

    @Test
    @Transactional
    public void testFilterResources() {
        final String expectedType = "RESOURCE";

        final Huxtable huxtable1 = TestData.huxtable(expectedType);
        final Huxtable huxtable2 = TestData.huxtable("OTHER");
        repository.saveAndFlush(huxtable1);
        repository.saveAndFlush(huxtable2);

        // TODO: before we get here, we should define what an "event" is that can be filtered
        // TODO: is it a RowChangeEvent? -> then we need a library
            // lib: RowChangeEvent + Jackson (so tiny)
        // TODO: then parse JSONs from consumer into event objects
        // TODO: filteredEvents = service.filterResources(events);
        // TODO: assert that filtered list contains 1
    }
}
