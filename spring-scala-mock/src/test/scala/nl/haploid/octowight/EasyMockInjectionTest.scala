package nl.haploid.octowight

import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import org.springframework.beans.factory.annotation.Autowired

class TestSubject {
  @Autowired val collaborator1: Collaborator1 = null
  @Autowired val collaborator2: Collaborator2 = null
}

class Collaborator1 {

}

class Collaborator2 {

}

class EasyMockInjectionTest extends FlatSpec with EasyMockInjection with ShouldMatchers with BeforeAndAfterEach {
  @Tested val testSubject: TestSubject = null
  @Mocked val collaborator1: Collaborator1 = null
  @Mocked val collaborator2: Collaborator2 = null

  override def beforeEach() = injectMocks()

  "Mocked objects" should "be injected into test instance" in {
    collaborator1 should not be null
    collaborator2 should not be null
  }

  "Mocked objects" should "be injected into test subject" in {
    testSubject.collaborator1 should not be null
    testSubject.collaborator2 should not be null
  }
}
