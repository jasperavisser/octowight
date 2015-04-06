package nl.haploid.event.channel.repository;

import nl.haploid.event.channel.AbstractIT;
import nl.haploid.event.channel.TestData;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RowChangeEventRepositoryIT extends AbstractIT {

    @Autowired
    private RowChangeEventRepository repository;

    @Test
    @Transactional
    public void testFindAll() {
        final RowChangeEvent event = TestData.rowChangeEvent();
        repository.saveAndFlush(event);
        final List<RowChangeEvent> events = repository.findAll();
        Assert.assertEquals(1, events.size());
    }
}
