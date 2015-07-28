package nl.haploid.octowight

import com.fasterxml.jackson.annotation.JsonIgnore

case class AtomChangeEvent(id: Long, atomId: Long, atomCategory: String, atomOrigin: String) {

  @JsonIgnore def atomGroup = new AtomGroup(origin = atomOrigin, category = atomCategory)
}
