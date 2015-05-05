package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import nl.haploid.octowight.registry.data.ResourceRoot;
import org.springframework.stereotype.Component;

@Component
public class ResourceModelIdFactory {

	public ResourceModelId resourceModelId(final Resource resource) {
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resource.getId());
		resourceModelId.setResourceType(resource.getType());
		return resourceModelId;
	}

	public ResourceModelId resourceModelId(final ResourceRoot resourceRoot) {
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resourceRoot.getResourceId());
		resourceModelId.setResourceType(resourceRoot.getResourceType());
		return resourceModelId;
	}
}
