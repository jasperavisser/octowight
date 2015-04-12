package nl.haploid.resource.detector.service;

import nl.haploid.event.RowChangeEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class MockResourceDetector implements ResourceDetector {

	@Override
	public Collection<String> getTableNames() {
		return null;
	}

	@Override
	public List<ResourceDescriptor> detect(final List<RowChangeEvent> events) {
		return null;
	}
}
