package nl.haploid.octowight.registry.repository

import java.lang

import nl.haploid.octowight.registry.data.{Atom, Atomizable}

object AtomDmo {

  def apply(atom: Atom): AtomDmo = new AtomDmo(id = atom.id, category = atom.category, origin = atom.origin)

  def apply(atomizable: Atomizable): AtomDmo = apply(atomizable.toAtom)
}

case class AtomDmo(id: lang.Long, origin: String, category: String)
