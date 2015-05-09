package nl.haploid.octowight

import java.lang

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder}
import org.codehaus.jackson.annotate.JsonIgnore

import scala.beans.BeanProperty

class AtomChangeEvent {
  @BeanProperty var id: lang.Long = _
  @BeanProperty var atomId: lang.Long = _
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomType: String = _

  @JsonIgnore def getAtomGroup = {
    val atomGroup = new AtomGroup
    atomGroup.setAtomOrigin(getAtomOrigin)
    atomGroup.setAtomType(getAtomType)
    atomGroup
  }

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)
}
