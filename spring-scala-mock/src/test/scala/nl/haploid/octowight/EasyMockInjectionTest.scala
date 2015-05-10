package nl.haploid.octowight

import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import org.springframework.beans.factory.annotation.Autowired

class TestSubjectSuper {
  @Autowired val collaborator1: Collaborator1 = null
  @Autowired val collaborator2: Collaborator2 = null
}

class TestSubject extends TestSubjectSuper

class Collaborator1

class Collaborator2

class EasyMockInjectionTest extends FlatSpec with EasyMockInjection with ShouldMatchers with BeforeAndAfterEach {
  @Tested val testSubjectSuper: TestSubjectSuper = null
  @Tested val testSubject: TestSubject = null
  @Mocked val collaborator1: Collaborator1 = null
  @Mocked val collaborator2: Collaborator2 = null

  override def beforeEach() = injectMocks()

  "Mocked objects" should "be injected into test instance" in {
    collaborator1 should not be null
    collaborator2 should not be null
  }

  "Mocked objects" should "be injected into fields of test subject" in {
    testSubjectSuper.collaborator1 should not be null
    testSubjectSuper.collaborator2 should not be null
  }

  "Mocked objects" should "be injected into super class fields of test subject" in {
    testSubject.collaborator1 should not be null
    testSubject.collaborator2 should not be null
  }
}
