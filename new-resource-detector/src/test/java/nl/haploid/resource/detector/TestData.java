package nl.haploid.resource.detector;

import nl.haploid.resource.detector.repository.Huxtable;

public class TestData {

    public static Huxtable huxtable(final String type) {
        final Huxtable event = new Huxtable();
        event.setType(type);
        return event;
    }
}
