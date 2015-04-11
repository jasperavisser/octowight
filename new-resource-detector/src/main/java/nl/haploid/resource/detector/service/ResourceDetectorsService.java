package nl.haploid.resource.detector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceDetectorsService {

    @Autowired
    private List<ResourceDetector> detectors;

    protected List<ResourceDetector> getDetectors() {
        return detectors;
    }

    public List<ResourceDetector> getDetectorsForTable(final String tableName) {
        return this.getDetectors().stream()
                .filter(d -> d.getTableNames().contains(tableName))
                .collect(Collectors.toList());
    }
}
