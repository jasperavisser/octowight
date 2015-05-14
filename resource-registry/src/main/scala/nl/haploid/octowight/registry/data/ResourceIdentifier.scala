package nl.haploid.octowight.registry.data

import java.lang

trait ResourceIdentifier {

  def getId: lang.Long

  def getType: String
}
