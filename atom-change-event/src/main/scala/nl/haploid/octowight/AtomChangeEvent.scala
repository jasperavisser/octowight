package nl.haploid.octowight

import com.fasterxml.jackson.annotation.JsonIgnore

case class AtomChangeEvent(id: Long, category: String, origin: String) {

  @JsonIgnore def atomGroup = new AtomGroup(origin = origin, category = category)
}
