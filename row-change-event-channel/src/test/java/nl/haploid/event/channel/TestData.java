package nl.haploid.event.channel;

import nl.haploid.event.RowChangeEvent;
import nl.haploid.event.channel.repository.RowChangeEventDmo;

import java.util.Random;

public class TestData {

    public static final String TABLE_NAME = "spike";

    public static RowChangeEvent rowChangeEvent() {
        final RowChangeEvent event = new RowChangeEvent();
        event.setTableName(TABLE_NAME);
        event.setRowId((long) new Random().nextInt());
        return event;
    }

    public static RowChangeEventDmo rowChangeEventDmo() {
        final RowChangeEventDmo event = new RowChangeEventDmo();
        event.setTableName(TABLE_NAME);
        event.setRowId((long) new Random().nextInt());
        return event;
    }
}
