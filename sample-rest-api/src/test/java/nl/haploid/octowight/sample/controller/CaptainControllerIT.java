package nl.haploid.octowight.sample.controller;

import nl.haploid.octowight.registry.repository.ResourceRootDmo;
import nl.haploid.octowight.registry.repository.ResourceRootDmoRepository;
import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.data.CaptainResource;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
import nl.haploid.octowight.sample.repository.RoleDmo;
import nl.haploid.octowight.sample.repository.RoleDmoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class CaptainControllerIT extends AbstractIT {

	private MockMvc mockMvc;

	@Autowired
	private CaptainController controller;

	@Autowired
	private PersonDmoRepository personDmoRepository;

	@Autowired
	private ResourceRootDmoRepository resourceRootDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	private final List<ResourceRootDmo> tempResourceRootDmos = new ArrayList<>();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		roleDmoRepository.deleteAllInBatch();
		personDmoRepository.deleteAllInBatch();
	}

	@Test
	public void testGetCaptain() throws Exception {
		final PersonDmo personDmo = personDmoRepository.saveAndFlush(TestData.personDmo());
		final RoleDmo roleDmo = roleDmoRepository.saveAndFlush(TestData.roleDmo(personDmo, CaptainResource.ResourceType()));
		final ResourceRootDmo resourceRootDmo = new ResourceRootDmo();
		resourceRootDmo.setAtomId(roleDmo.getId());
		resourceRootDmo.setAtomType(RoleDmo.AtomType());
		resourceRootDmo.setAtomOrigin("the seven seas");
		resourceRootDmo.setResourceId(TestData.nextLong());
		resourceRootDmo.setResourceType(CaptainResource.ResourceType());
		final ResourceRootDmo resourceRootDmoWithId = resourceRootDmoRepository.save(resourceRootDmo);
		tempResourceRootDmos.add(resourceRootDmoWithId);
		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/captain/%d", resourceRootDmoWithId.getResourceId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@After
	public void teardown() {
		resourceRootDmoRepository.delete(tempResourceRootDmos);
	}
}
