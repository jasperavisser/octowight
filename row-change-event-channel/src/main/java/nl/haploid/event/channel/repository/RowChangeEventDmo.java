package nl.haploid.event.channel.repository;

import javax.persistence.*;

// TODO: rename to Dmo
@Entity
@Table(name = "row_change_events", schema = "octowight")
public class RowChangeEventDmo {

    @Id
    @SequenceGenerator(name = "event_sequence", schema = "octowight")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "table_name")
    private String tableName;

    @Column(name = "row_id")
    private Long rowId;

    public Long getId() {
        return id;
    }

    public Long getRowId() {
        return rowId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
