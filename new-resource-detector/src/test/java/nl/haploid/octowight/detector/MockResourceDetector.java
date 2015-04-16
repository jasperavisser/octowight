package nl.haploid.octowight.detector;

import nl.haploid.octowight.AtomChangeEvent;
import nl.haploid.octowight.data.Resource;
import nl.haploid.octowight.data.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MockResourceDetector implements ResourceDetector {

	public static final String ATOM_TYPE = "john";

	public static final String RESOURCE_TYPE = "preston";

	@Autowired
	private ResourceFactory resourceFactory;

	@Override
	public Collection<String> getAtomTypes() {
		return Collections.singletonList(ATOM_TYPE);
	}

	@Override
	public List<Resource> detect(final List<AtomChangeEvent> events) {
		return events.stream()
				.map(event -> resourceFactory.fromAtomChangeEvent(event, RESOURCE_TYPE))
				.collect(Collectors.toList());
	}
}
