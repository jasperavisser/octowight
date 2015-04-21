package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.AbstractIT;
import nl.haploid.octowight.registry.TestData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResourceElementDmoRepositoryIT extends AbstractIT {

    @Autowired
    private ResourceElementDmoRepository repository;

    @Test
    public void findByAtomIdAndAtomTypeAndAtomLocus() {
        final ResourceElementDmo dmo = TestData.resourceElementDmo();
        final ResourceElementDmo expectedDmo = repository.save(dmo);
        final ResourceElementDmo actualDmo = repository
                .findByAtomIdAndAtomTypeAndAtomLocus(expectedDmo.getAtomId(), expectedDmo.getAtomType(), expectedDmo.getAtomLocus());
        assertEquals(expectedDmo, actualDmo);
    }
}
