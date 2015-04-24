package nl.haploid.octowight.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.registry.data.ResourceRoot;

import java.util.Collection;
import java.util.List;

public interface ResourceDetector { // TODO: rename?

	Collection<String> getAtomTypes();

	List<ResourceRoot> detect(final List<AtomChangeEvent> events);
}
