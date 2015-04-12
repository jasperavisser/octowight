package nl.haploid.resource.detector.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ResourceDescriptorTest {

	@Test
	public void testGetKey() {
		final ResourceDescriptor descriptor = new ResourceDescriptor();
		descriptor.setResourceType("cosgrove");
		descriptor.setRowId(69l);
		descriptor.setTableName("campbell");
		final String expectedKey = "campbell/69->cosgrove";
		final String actualKey = descriptor.getKey();
		assertEquals(expectedKey, actualKey);
	}
}
