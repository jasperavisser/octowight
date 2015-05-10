package nl.haploid.octowight.registry.data

import java.lang

import scala.beans.BeanProperty

abstract class Resource[M <: Model] {

  @BeanProperty var version: lang.Long = _

  def getAtoms: Traversable[Atom]

  def getId: lang.Long

  def getType: String

  def getModel: M
}
