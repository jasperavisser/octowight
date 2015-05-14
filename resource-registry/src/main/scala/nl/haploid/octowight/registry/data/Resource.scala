package nl.haploid.octowight.registry.data

import java.lang

import scala.beans.BeanProperty

abstract class Resource[M <: Model] extends ResourceIdentifier {

  @BeanProperty var version: lang.Long = _

  def getAtoms: Traversable[Atom]

  override def getId: lang.Long

  override def getType: String

  def getModel: M
}
