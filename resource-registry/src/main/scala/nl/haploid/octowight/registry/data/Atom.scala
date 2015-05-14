package nl.haploid.octowight.registry.data

import java.lang

trait Atom {

  // TODO: get rid of the superfluous Atom infix
  def getAtomId: lang.Long

  def getAtomOrigin: String

  def getAtomType: String
}
