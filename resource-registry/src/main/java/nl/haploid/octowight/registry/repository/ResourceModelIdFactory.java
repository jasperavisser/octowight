package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceModelIdFactory {

	public ResourceModelId resourceModelId(final Resource resource) {
		final ResourceModelId resourceModelId = new ResourceModelId();
		resourceModelId.setResourceId(resource.getId());
		resourceModelId.setResourceType(resource.getType());
		return resourceModelId;
	}
}
