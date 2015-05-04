package nl.haploid.octowight.service;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.AtomGroup;
import nl.haploid.octowight.detector.ResourceDetector;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceDetectorsService { // TODO: rename?

	@Autowired
	private List<ResourceDetector> detectors;

	protected List<ResourceDetector> getDetectors() {
		return detectors;
	}

	public List<ResourceDetector> getDetectorsForAtomType(final String atomType) {
		return this.getDetectors().stream()
				.filter(d -> d.getAtomTypes().contains(atomType))
				.collect(Collectors.toList());
	}

	public List<ResourceRoot> detectResources(final AtomGroup atomGroup, final List<AtomChangeEvent> events) {
		return getDetectorsForAtomType(atomGroup.getAtomType()).stream()
				.map(d -> d.detect(events))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
