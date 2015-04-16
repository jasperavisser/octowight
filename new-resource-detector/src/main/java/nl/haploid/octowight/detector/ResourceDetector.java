package nl.haploid.octowight.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.data.Resource;

import java.util.Collection;
import java.util.List;

public interface ResourceDetector {

	Collection<String> getAtomTypes();

	List<Resource> detect(final List<AtomChangeEvent> events);
}
