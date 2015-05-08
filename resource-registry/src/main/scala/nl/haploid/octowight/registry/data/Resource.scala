package nl.haploid.octowight.registry.data

import java.{lang, util}

import scala.beans.BeanProperty

abstract class Resource[T <: Model] {

  @BeanProperty var version: lang.Long = _

  def getAtoms: util.Collection[Atom] // TODO: Traversable

  def getId: lang.Long

  def getType: String

  def getModel: T
}
