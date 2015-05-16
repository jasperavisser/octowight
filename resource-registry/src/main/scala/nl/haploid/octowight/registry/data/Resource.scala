package nl.haploid.octowight.registry.data

import java.lang

abstract class Resource[M <: Model] {

  def atoms: Traversable[Atom]

  def collection: String

  def model: M

  def id: lang.Long

  def version: lang.Long
}
