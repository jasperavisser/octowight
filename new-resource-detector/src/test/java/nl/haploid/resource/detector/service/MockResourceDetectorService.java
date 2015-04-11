package nl.haploid.resource.detector.service;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MockResourceDetectorService implements ResourceDetector {

    @Override
    public Collection<String> getTableNames() {
        return null;
    }
}
