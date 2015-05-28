package nl.haploid.octowight.sample.repository

import slick.driver.PostgresDriver.api._

class AtomChangeEventTable(tag: Tag) extends Table[AtomChangeEventDmo](tag, Some("octowight"), "atom_change_event") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def atomId = column[Long]("atom_id")

  def atomCategory = column[String]("atom_category")

  def atomOrigin = column[String]("atom_origin")

  def * = (id, atomId, atomCategory, atomOrigin) <>(
    (AtomChangeEventDmo.apply _).tupled, AtomChangeEventDmo.unapply)
}