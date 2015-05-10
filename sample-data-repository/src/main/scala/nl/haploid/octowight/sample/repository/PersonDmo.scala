package nl.haploid.octowight.sample.repository

import java.{lang, util}
import javax.persistence._

import nl.haploid.octowight.registry.data.Atom

import scala.beans.BeanProperty

object PersonDmo {
  val AtomType = "person"
}

@Entity
@Table(name = "person", schema = "octowight")
class PersonDmo extends Atom {

  @Id
  @SequenceGenerator(name = "person_sequence", sequenceName = "octowight.person_sequence")
  @GeneratedValue(generator = "person_sequence")
  @BeanProperty var id: lang.Long = null

  @Column(name = "name")
  @BeanProperty var name: String = null

  @OneToMany(mappedBy = "person")
  // TODO: can this be a scala Iterable?
  @BeanProperty var roles: util.List[RoleDmo] = new util.ArrayList[RoleDmo]

  @Transient
  @BeanProperty var atomOrigin: String = null

  override def getAtomId = id

  def getAtomType = PersonDmo.AtomType
}
