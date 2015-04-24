package nl.haploid.octowight.sample.service;

import nl.haploid.octowight.sample.data.CaptainModel;
import nl.haploid.octowight.sample.data.CaptainResource;
import org.springframework.stereotype.Service;

@Service
public class CaptainService extends AbstractResourceService<CaptainModel, CaptainResource> {

	@Override
	public String getResourceType() {
		return CaptainResource.RESOURCE_TYPE;
	}
}
