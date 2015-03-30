package nl.haploid.event.channel.repository

import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.GenerationType
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "row_change_events", schema = "projectx")
class RowChangeEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long = _

	@Column(name = "table_name")
	var tableName: String = _

	@Column(name = "row_id")
	var rowId: Long = _
}
