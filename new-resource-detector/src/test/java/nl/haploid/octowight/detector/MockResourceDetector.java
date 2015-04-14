package nl.haploid.octowight.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.service.ResourceDescriptor;
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
