package nl.haploid.octowight.sample.data;

import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceRoot;

public interface ResourceFactory<R extends Resource> {

	R fromResourceRoot(final ResourceRoot resourceRoot);
}
