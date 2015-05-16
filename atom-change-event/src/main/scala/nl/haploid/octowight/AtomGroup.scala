package nl.haploid.octowight

import org.apache.commons.lang3.builder.{EqualsBuilder, HashCodeBuilder, ToStringBuilder}

import scala.beans.BeanProperty

object AtomGroup {

  def apply(origin: String, category: String): AtomGroup = {
    val g = new AtomGroup
    g.setAtomCategory(category)
    g.setAtomOrigin(origin)
    g
  }
}

class AtomGroup {
  // TODO: remove atom prefix
  @BeanProperty var atomOrigin: String = _
  @BeanProperty var atomCategory: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
