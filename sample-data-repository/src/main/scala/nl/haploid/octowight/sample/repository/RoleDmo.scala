package nl.haploid.octowight.sample.repository

import java.lang
import javax.persistence._

import nl.haploid.octowight.registry.data.{Atom, Atomizable}

import scala.beans.BeanProperty

object RoleDmo {
  val AtomType = "role"
}

@Entity
@Table(name = "role", schema = "octowight")
class RoleDmo extends Atomizable {

  @Id
  @SequenceGenerator(name = "role_sequence", sequenceName = "octowight.role_sequence")
  @GeneratedValue(generator = "role_sequence")
  @BeanProperty var id: lang.Long = null

  @ManyToOne
  @JoinColumn(name = "person")
  @BeanProperty var person: PersonDmo = null

  @Column(name = "name")
  @BeanProperty var name: String = null

  @Transient
  @BeanProperty var origin: String = null

  override def toAtom = new Atom(id, origin, RoleDmo.AtomType)
}
