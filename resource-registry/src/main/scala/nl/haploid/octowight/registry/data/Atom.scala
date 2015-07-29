package nl.haploid.octowight.registry.data

import nl.haploid.octowight.AtomGroup

object Atom {

  def apply(group: AtomGroup, id: Long): Atom = Atom(id = id, category = group.category, origin = group.origin)
}

case class Atom(id: Long, origin: String, category: String)
