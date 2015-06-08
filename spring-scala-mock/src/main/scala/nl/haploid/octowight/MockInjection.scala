package nl.haploid.octowight

import java.lang.annotation.Annotation
import java.lang.reflect.Field

import org.slf4j.{Logger, LoggerFactory}


object MockInjection {
  val Autowired: Class[Annotation] = Class.forName("org.springframework.beans.factory.annotation.Autowired")
    .asInstanceOf[Class[Annotation]]
}

trait MockInjection {
  private[this] val log: Logger = LoggerFactory.getLogger(getClass)

  private[this] val mockeds: Map[Class[_], Field] = getFieldsByAnnotation(getClass, classOf[Mocked])
  private[this] val testeds: Map[Class[_], Field] = getFieldsByAnnotation(getClass, classOf[Tested])

  log.debug(s"Found ${mockeds.size} mocked objects")
  log.debug(s"Found ${testeds.size} tested objects")

  def createMockInstance(mockedField: Field): AnyRef

  def createPartialMockInstance(mockedField: Field): AnyRef

  def injectMocks(): Unit = {
    mockeds.foreach {
      case (mockedType, mockedField) =>
        log.debug(s"Inject mock instance of ${mockedField.getType.getCanonicalName} into ${this.getClass.getCanonicalName}")
        mockedField.set(this, createMockInstance(mockedField))
    }
    testeds.foreach {
      case (testedType, testedField) =>
        log.debug(s"Inject mock instance of ${testedType.getCanonicalName} into ${this.getClass.getCanonicalName}")
        testedField.set(this, createPartialMockInstance(testedField))
        injectMocks(testedField.get(this))
    }
  }

  def withMocks[T <: AnyRef](testedObject: T): T = {
    injectMocks(testedObject)
    testedObject
  }

  private[this] def injectMocks(testedObject: AnyRef): Unit = {
    getSuperClassesAndInterfaces(testedObject.getClass).foreach(injectMocks(_, testedObject))
  }

  private[this] def injectMocks(testedType: Class[_], testedObject: AnyRef): Unit = {
    val fields = getFieldsByAnnotation(testedType, MockInjection.Autowired)
    log.debug(s"Found ${fields.size} autowired fields in ${testedType.getCanonicalName}")
    fields.foreach {
      case (autowiredClazz, autowiredField) =>
        injectMock(autowiredField, testedObject)
    }
  }

  private[this] def injectMock(autowiredField: Field, testedObject: AnyRef): Unit = {
    autowiredField.setAccessible(true)
    val option = mockeds.get(autowiredField.getType)
    option match {
      case Some(mockedField) =>
        log.debug(s"Inject mock instance of ${mockedField.getType.getCanonicalName} into ${testedObject.getClass.getCanonicalName}")
        autowiredField.set(testedObject, mockedField.get(this))
      case None => throw new RuntimeException(s"No mocked instance found for ${autowiredField.getType.getCanonicalName}!")
    }
  }

  def getSuperClassesAndInterfaces(clazz: Class[_]): List[Class[_]] = {
    clazz match {
      case null => List()
      case _ => clazz :: clazz.getInterfaces.toList ::: getSuperClassesAndInterfaces(clazz.getSuperclass)
    }
  }

  private[this] def getFieldsByAnnotation(clazz: Class[_], annotation: Class[_ <: Annotation]): Map[Class[_], Field] = {
    getSuperClassesAndInterfaces(clazz)
    val fields = clazz.getDeclaredFields.filter {
      f => Option(f.getAnnotation(annotation)).nonEmpty
    }
    val mapField = (f: Field) => {
      f.setAccessible(true)
      f.getType -> f
    }
    fields.map(mapField).toMap
  }
}
