package nl.haploid.resource.detector;

import nl.haploid.resource.detector.service.ResourceDescriptor;

import java.util.Random;
import java.util.UUID;

public class TestData {

    public static long nextLong() {
        return new Random().nextLong();
    }

    public static ResourceDescriptor resourceDescriptor(final Long resourceId) {
        final ResourceDescriptor descriptor = new ResourceDescriptor();
        descriptor.setResourceId(resourceId);
        descriptor.setResourceType("olson");
        descriptor.setRowId(nextLong());
        descriptor.setTableName("draper");
        return descriptor;
    }

    public static String topic() {
        return UUID.randomUUID().toString();
    }
}
