package nl.haploid.octowight.controller;

import nl.haploid.octowight.AbstractIT;
import nl.haploid.octowight.TestData;
import nl.haploid.octowight.registry.repository.ResourceDmo;
import nl.haploid.octowight.registry.repository.ResourceDmoRepository;
import nl.haploid.octowight.repository.BookDmo;
import nl.haploid.octowight.repository.BookDmoRepository;
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

public class BookControllerIT extends AbstractIT {

	private MockMvc mockMvc;

	@Autowired
	private BookController controller;

	@Autowired
	private BookDmoRepository bookDmoRepository;

	@Autowired
	private ResourceDmoRepository resourceDmoRepository;

	private final List<ResourceDmo> tempResourceDmos = new ArrayList<>();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		bookDmoRepository.deleteAllInBatch();
	}

	@Test
	public void testGetBooks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/book"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetBook() throws Exception {
		final BookDmo bookDmo = bookDmoRepository.save(TestData.bookDmo("scifi"));
		final ResourceDmo resourceDmo = new ResourceDmo();
		resourceDmo.setAtomId(bookDmo.getId());
		resourceDmo.setAtomType("book");
		resourceDmo.setAtomLocus("somewhere");
		resourceDmo.setResourceId(TestData.nextLong());
		resourceDmo.setResourceType(BookService.RESOURCE_TYPE);
		final ResourceDmo resourceDmoWithId = resourceDmoRepository.save(resourceDmo);
		tempResourceDmos.add(resourceDmoWithId);
		mockMvc.perform(MockMvcRequestBuilders.get(String.format("/book/%d", resourceDmoWithId.getResourceId())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
	}

	@After
	public void teardown() {
		resourceDmoRepository.delete(tempResourceDmos);
	}
}
