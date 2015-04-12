package nl.haploid.resource.detector.service;

import nl.haploid.event.RowChangeEvent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MockResourceDetectorService implements ResourceDetector {

    @Override
    public Collection<String> getTableNames() {
        return null;
    }

    @Override
    public List<ResourceDescriptor> detect(List<RowChangeEvent> events) {
        return null;
    }
}
