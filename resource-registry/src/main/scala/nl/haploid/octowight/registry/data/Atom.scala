package nl.haploid.octowight.registry.data

import java.lang

trait Atom {

  def getAtomId: lang.Long

  def getAtomOrigin: String

  def getAtomType: String
}
