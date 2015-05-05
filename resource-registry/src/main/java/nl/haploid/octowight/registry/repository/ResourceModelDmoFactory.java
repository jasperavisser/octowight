package nl.haploid.octowight.registry.repository;

import nl.haploid.octowight.registry.data.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceModelDmoFactory {

	@Autowired
	private ResourceModelIdFactory resourceModelIdFactory;

	public ResourceModelDmo fromResourceAndBody(final Resource resource, final String body) {
		final ResourceModelDmo resourceModelDmo = new ResourceModelDmo();
		final ResourceModelId resourceModelId = resourceModelIdFactory.resourceModelId(resource);
		resourceModelDmo.setId(resourceModelId);
		resourceModelDmo.setBody(body);
		resourceModelDmo.setVersion(resource.getVersion());
		return resourceModelDmo;
	}
}
