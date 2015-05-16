package nl.haploid.octowight

import java.lang

import com.fasterxml.jackson.annotation.JsonIgnore

case class AtomChangeEvent(id: lang.Long, atomId: lang.Long, atomCategory: String, atomOrigin: String) {

  @JsonIgnore def atomGroup = new AtomGroup(origin = atomOrigin, category = atomCategory)
}
