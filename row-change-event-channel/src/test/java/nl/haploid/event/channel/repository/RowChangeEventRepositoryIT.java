package nl.haploid.event.channel.repository;

import nl.haploid.event.channel.AbstractIT;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RowChangeEventRepositoryIT extends AbstractIT {

    @Autowired
    private RowChangeEventRepository repository;

    @Test
    public void testFindAll() {
        final RowChangeEvent event = new RowChangeEvent();
        event.setTableName("corvax");
        event.setRowId(123l);
        repository.save(event);
        List<RowChangeEvent> events = repository.findAll();
        Assert.assertEquals(1, events.size());
    }
}
