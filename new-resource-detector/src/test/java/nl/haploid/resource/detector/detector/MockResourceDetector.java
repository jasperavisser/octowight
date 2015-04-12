package nl.haploid.resource.detector.detector;

import nl.haploid.event.AtomChangeEvent;
import nl.haploid.resource.detector.service.ResourceDescriptor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MockResourceDetector implements ResourceDetector {

	@Override
	public Collection<String> getAtomTypes() {
		return null;
	}

	@Override
	public List<ResourceDescriptor> detect(final List<AtomChangeEvent> events) {
		return null;
	}
}
