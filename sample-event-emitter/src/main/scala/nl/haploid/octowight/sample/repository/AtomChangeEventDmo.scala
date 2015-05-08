package nl.haploid.octowight.sample.repository

import javax.persistence._

import scala.beans.BeanProperty

@Entity
@Table(name = "atom_change_event", schema = "octowight")
class AtomChangeEventDmo {

  @Id
  @SequenceGenerator(name = "event_sequence", sequenceName = "octowight.event_sequence")
  @GeneratedValue(generator = "event_sequence")
  @BeanProperty var id: Long = _

  @Column(name = "atom_id")
  @BeanProperty var atomId: Long = _

  @Column(name = "atom_origin")
  @BeanProperty var atomOrigin: String = _

  @Column(name = "atom_type")
  @BeanProperty var atomType: String = _
}
