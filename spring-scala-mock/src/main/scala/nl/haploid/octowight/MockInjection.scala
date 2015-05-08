package nl.haploid.octowight

import java.lang.annotation.Annotation
import java.lang.reflect.Field

import org.slf4j.LoggerFactory

object MockInjection {
  val Autowired = Class.forName("org.springframework.beans.factory.annotation.Autowired")
    .asInstanceOf[Class[Annotation]]
}

trait MockInjection {
  protected val log = LoggerFactory.getLogger(getClass)

  private val mockeds = getFieldsByAnnotation(getClass, classOf[Mocked])
  private val testeds = getFieldsByAnnotation(getClass, classOf[Tested])

  def createMockInstance(mockedField: Field): AnyRef

  def injectMocks() = {
    mockeds.foreach {
      case (clazz, field) =>
        log.debug(s"Inject mock instance of ${field.getType.getCanonicalName} into ${this.getClass.getCanonicalName}")
        field.set(this, createMockInstance(field))
    }
    testeds.foreach {
      case (clazz, field) => {
        val testedObject = field.get(this)
        if (testedObject == null) {
          throw new RuntimeException(s"Tested object of ${clazz.getCanonicalName} is null")
        }
        getFieldsByAnnotation(clazz, MockInjection.Autowired).foreach {
          case (autowiredClazz, autowiredField) =>
            injectMock(autowiredField, testedObject)
        }
      }
    }
  }

  private def injectMock(autowiredField: Field, testedObject: AnyRef) = {
    autowiredField.setAccessible(true)
    val option = mockeds.get(autowiredField.getType)
    option match {
      case Some(_) =>
        val field = option.get
        log.debug(s"Inject mock instance of ${field.getType.getCanonicalName} into ${testedObject.getClass.getCanonicalName}")
        autowiredField.set(testedObject, field.get(this))
      case None => throw new RuntimeException(s"No mocked instance found for ${autowiredField.getType.getCanonicalName}!")
    }
  }

  private def getFieldsByAnnotation(clazz: Class[_], annotation: Class[_ <: Annotation]): Map[Class[_], Field] = {
    val fields = clazz.getDeclaredFields.filter(_ hasAnnotation annotation)
    val mapField = (f: Field) => {
      f.setAccessible(true)
      f.getType -> f
    }
    fields.map(mapField).toMap
  }

  private implicit def fieldHasAnnotation(field: Field): Object {def hasAnnotation(annotation: Class[_ <: Annotation]): Boolean} = new {
    def hasAnnotation(annotation: Class[_ <: Annotation]) = field.getAnnotation(annotation) != null
  }
}
