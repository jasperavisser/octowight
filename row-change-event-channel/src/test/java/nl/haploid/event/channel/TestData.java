package nl.haploid.event.channel;

import nl.haploid.event.channel.repository.RowChangeEvent;

import java.util.Random;

public class TestData {

    public static final String TABLE_NAME = "spike";

    public static RowChangeEvent rowChangeEvent() {
        final RowChangeEvent event = new RowChangeEvent();
        event.setTableName(TABLE_NAME);
        event.setRowId(Long.valueOf(new Random().nextInt()));
        return event;
    }
}
