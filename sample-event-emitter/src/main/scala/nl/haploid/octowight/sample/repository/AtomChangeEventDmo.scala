package nl.haploid.octowight.sample.repository

import java.lang
import javax.persistence._

import nl.haploid.octowight.AtomChangeEvent

import scala.annotation.meta.field

@Entity
@Table(name = "atom_change_event", schema = "octowight")
case class AtomChangeEventDmo
(
  @(Id@field)
  @(SequenceGenerator@field)(name = "event_sequence", sequenceName = "octowight.event_sequence")
  @(GeneratedValue@field)(generator = "event_sequence")
  id: lang.Long,
  @(Column@field)(name = "atom_id")
  atomId: lang.Long,
  @(Column@field)(name = "atom_origin")
  atomOrigin: String,
  @(Column@field)(name = "atom_category")
  atomCategory: String) {

  def this() =
    this(id = null, atomId = null, atomOrigin = null, atomCategory = null)

  def toAtomChangeEvent =
    new AtomChangeEvent(id = id, atomId = atomId, atomOrigin = atomOrigin, atomCategory = atomCategory)
}
