package nl.haploid.octowight.sample.repository

import nl.haploid.octowight.AtomChangeEvent

case class AtomChangeEventDmo(id: Long, atomId: Long, atomCategory: String, atomOrigin: String) {

  def toAtomChangeEvent =
    new AtomChangeEvent(id = id, atomId = atomId, atomOrigin = atomOrigin, atomCategory = atomCategory)
}
