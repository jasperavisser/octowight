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

  def resourceRoot(resourceId: Long) = {
    val resourceRoot = new ResourceRoot
    resourceRoot.setRoot(new Atom(nextLong, nextString, nextString))
    resourceRoot.setResourceId(resourceId)
    resourceRoot.setResourceType(nextString)
    resourceRoot.setVersion(nextLong)
    resourceRoot
  }

  def resourceRootDmo = {
    val resourceRootDmo = new ResourceRootDmo
    resourceRootDmo.setRoot(new AtomDmo)
    resourceRootDmo.setResourceId(nextLong)
    resourceRootDmo
  }

  def roleDmo(personDmo: PersonDmo, name: String) = {
    val dmo = new RoleDmo
    dmo.setId(nextLong)
    dmo.setPerson(personDmo)
    dmo.setName(name)
    dmo
  }

  def mockModel() = new MockModel {}

  def mockResource(atoms: Set[Atom]) = {
    new MockResource {
      val id = nextLong
      val model = mockModel()

      override def getType: String = MockResource.Type

      override def getId: lang.Long = id

      override def getModel: MockModel = model

      override def getAtoms: Traversable[Atom] = atoms
    }
  }
}

abstract class MockModel extends Model

object MockResource {
  val Type = TestData.nextString
}

abstract class MockResource extends Resource[MockModel]

abstract class MockResourceService extends AbstractResourceService[MockModel, MockResource]
