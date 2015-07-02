package nl.haploid.octowight.sample

import java.util.{Random, UUID}
import nl.haploid.octowight.registry.data.{ResourceMessage, Atom, ResourceIdentifier, ResourceRoot}
import nl.haploid.octowight.sample.repository.RoleDmo

object TestData {

  def atom = new Atom(id = nextLong, origin = nextString, category = nextString)

  def nextLong = new Random().nextLong

  def nextString = UUID.randomUUID.toString

  def resource = new ResourceMessage(resourceIdentifier = resourceIdentifier, model = nextString, tombstone = false)

  def resourceIdentifier = new ResourceIdentifier(collection = nextString, id = nextLong)

  def resourceRoot =
    new ResourceRoot(resourceId = nextLong, resourceCollection = "captain", root = atom, version = nextLong)

  def roleDmo = new RoleDmo
}
