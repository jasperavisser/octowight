package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, Atomizable}
import org.apache.commons.lang3.builder.{ToStringBuilder, HashCodeBuilder, EqualsBuilder}

import scala.beans.BeanProperty

object AtomDmo {

  def apply(atom: Atom): AtomDmo = {
    val d = new AtomDmo
    d.setId(atom.id)
    d.setCategory(atom.category)
    d.setOrigin(atom.origin)
    d
  }

  def apply(atomizable: Atomizable): AtomDmo = apply(atomizable.toAtom)
}

class AtomDmo {
  @BeanProperty var id: lang.Long = _
  @BeanProperty var origin: String = _
  @BeanProperty var category: String = _

  override def equals(that: Any) = EqualsBuilder.reflectionEquals(this, that, false)

  override def hashCode = HashCodeBuilder.reflectionHashCode(this, false)

  override def toString = ToStringBuilder.reflectionToString(this)
}
