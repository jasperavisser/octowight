package nl.haploid.resource.detector.detector;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.resource.detector.service.ResourceDescriptor;

import java.util.Collection;
import java.util.List;

public interface ResourceDetector {

	Collection<String> getAtomTypes();

	// TODO: do we want them grouped by atom type/locus?
	List<ResourceDescriptor> detect(final List<AtomChangeEvent> events);
}
