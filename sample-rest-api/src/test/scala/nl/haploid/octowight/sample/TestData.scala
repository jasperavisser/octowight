package nl.haploid.octowight.sample

import java.lang
import java.util.{Random, UUID}

import nl.haploid.octowight.registry.data.{Atom, Model, Resource, ResourceRoot}
import nl.haploid.octowight.registry.repository.{AtomDmo, ResourceRootDmo}
import nl.haploid.octowight.sample.repository.{PersonDmo, RoleDmo}
import nl.haploid.octowight.sample.service.AbstractResourceService

object TestData {

  def atom = new Atom(nextLong, nextString, nextString)

  def personDmo: PersonDmo = personDmo(null)

  def personDmo(id: lang.Long) = {
    val person = new PersonDmo
    person.setId(id)
    person.setName(nextString)
    person
  }

  def nextString = UUID.randomUUID.toString

  def nextLong: Long = new Random().nextLong

  def resourceRoot: ResourceRoot = resourceRoot(nextLong)

  def resourceRoot(resourceId: Long) =
    new ResourceRoot(resourceId = resourceId, resourceCollection = nextString, root = atom, version = null)

  def resourceRootDmo = {
    new ResourceRootDmo(root = AtomDmo(atom), resourceId = nextLong, resourceCollection = nextString, version = nextLong)
  }

  def roleDmo(personDmo: PersonDmo, name: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setName(name)
    dmo
  }

  def mockModel() = new MockModel {}

  def mockResource(_atoms: Set[Atom]) = {
    new MockResource(nextLong, nextLong) {
      val model = mockModel()

      override def atoms: Traversable[Atom] = _atoms

      override def collection: String = MockResource.ResourceCollection
    }
  }
}

abstract class MockModel extends Model

object MockResource {
  val ResourceCollection = TestData.nextString
}

abstract case class MockResource(id: lang.Long, version: lang.Long) extends Resource[MockModel]

abstract class MockResourceService extends AbstractResourceService[MockModel, MockResource]
