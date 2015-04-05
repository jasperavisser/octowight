package nl.haploid.event.channel.repository

import javax.persistence._

@Entity
@Table(name = "row_change_events", schema = "octowight")
class RowChangeEvent {

  @Id
  @SequenceGenerator(name = "event_sequence", schema = "octowight")
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  var id: Long = _

  @Column(name = "table_name")
  var tableName: String = _

  @Column(name = "row_id")
  var rowId: Long = _
}
