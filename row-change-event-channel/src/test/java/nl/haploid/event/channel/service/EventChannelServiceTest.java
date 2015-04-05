package nl.haploid.event.channel.service;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import nl.haploid.event.channel.repository.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventRepository;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class EventChannelServiceTest {

    @Tested
    private EventChannelService service;

    @Injectable
    private RowChangeEventRepository mockRepository;

    @Test
    public void testQueueRowChangeEvents() {
        final List<RowChangeEvent> expectedEvents = new ArrayList<RowChangeEvent>();
        new Expectations() {
            {
                mockRepository.findAll();
                times = 1;
                result = expectedEvents;

                mockRepository.delete(expectedEvents);
                times = 1;
            }
        };
        service.queueRowChangeEvents();
    }
}
