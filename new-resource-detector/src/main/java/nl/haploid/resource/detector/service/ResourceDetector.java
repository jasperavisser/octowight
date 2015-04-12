package nl.haploid.resource.detector.service;

import nl.haploid.event.RowChangeEvent;

import java.util.Collection;
import java.util.List;

public interface ResourceDetector {

    Collection<String> getTableNames();

    List<ResourceDescriptor> detect(final List<RowChangeEvent> events);
}