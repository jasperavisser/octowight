package nl.haploid.octowight

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FlatSpec, ShouldMatchers}
import org.springframework.beans.factory.annotation.Autowired

class TestSubjectSuper {
  @Autowired val collaborator1: Collaborator1 = null
  @Autowired val collaborator2: Collaborator2 = null
}

class TestSubject extends TestSubjectSuper

class Collaborator1

class Collaborator2

@RunWith(classOf[JUnitRunner])
class EasyMockInjectionTest extends FlatSpec with EasyMockInjection with ShouldMatchers with BeforeAndAfterEach {
  @Tested val testSubjectSuper: TestSubjectSuper = null
  @Tested val testSubject: TestSubject = null
  @Mocked val collaborator1: Collaborator1 = null
  @Mocked val collaborator2: Collaborator2 = null

  override def beforeEach() = injectMocks()

  behavior of "Easy mock injection"

  it should "inject mocks into test instance" in {
    collaborator1 should not be null
    collaborator2 should not be null
  }

  it should "inject mocks into fields of test subject" in {
    testSubjectSuper.collaborator1 should not be null
    testSubjectSuper.collaborator2 should not be null
  }

  it should "inject mocks into super class fields of test subject" in {
    testSubject.collaborator1 should not be null
    testSubject.collaborator2 should not be null
  }
}
