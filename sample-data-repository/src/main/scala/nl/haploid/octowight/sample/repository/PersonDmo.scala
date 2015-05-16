package nl.haploid.octowight.sample.repository

import java.{lang, util}
import javax.persistence._

import nl.haploid.octowight.registry.data.{Atom, Atomizable}

import scala.beans.BeanProperty

object PersonDmo {
  val AtomCategory = "person"
}

@Entity
@Table(name = "person", schema = "octowight")
class PersonDmo extends Atomizable {

  @Id
  @SequenceGenerator(name = "person_sequence", sequenceName = "octowight.person_sequence")
  @GeneratedValue(generator = "person_sequence")
  @BeanProperty var id: lang.Long = null

  @Column(name = "name")
  @BeanProperty var name: String = null

  @OneToMany(mappedBy = "person")
  @BeanProperty var roles: util.List[RoleDmo] = new util.ArrayList[RoleDmo]

  @Transient
  @BeanProperty var origin: String = null

  override def toAtom = new Atom(id, origin, RoleDmo.AtomCategory)
}
