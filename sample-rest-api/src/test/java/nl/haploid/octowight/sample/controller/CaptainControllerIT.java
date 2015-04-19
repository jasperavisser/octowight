package nl.haploid.octowight.sample.controller;

import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import nl.haploid.octowight.sample.AbstractIT;
import nl.haploid.octowight.sample.TestData;
import nl.haploid.octowight.sample.data.Captain;
import nl.haploid.octowight.sample.repository.PersonDmo;
import nl.haploid.octowight.sample.repository.PersonDmoRepository;
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
	private ResourceDmoRepository resourceDmoRepository;

	@Autowired
	private RoleDmoRepository roleDmoRepository;

	private final List<ResourceDmo> tempResourceDmos = new ArrayList<>();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		roleDmoRepository.deleteAllInBatch();
		personDmoRepository.deleteAllInBatch();
	}

	@Test
	public void testGetCaptains() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/captain"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetCaptain() throws Exception {
		final PersonDmo personDmo = personDmoRepository.save(TestData.personDmo());
		final ResourceDmo resourceDmo = new ResourceDmo();
		resourceDmo.setAtomId(personDmo.getId());
		resourceDmo.setAtomType(PersonDmo.ATOM_TYPE);
		resourceDmo.setAtomLocus("the seven seas");
		resourceDmo.setResourceId(TestData.nextLong());
		resourceDmo.setResourceType(Captain.RESOURCE_TYPE);
		final ResourceDmo resourceDmoWithId = resourceDmoRepository.save(resourceDmo);
		tempResourceDmos.add(resourceDmoWithId);
		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/captain/%d", resourceDmoWithId.getResourceId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@After
	public void teardown() {
		resourceDmoRepository.delete(tempResourceDmos);
	}
}
