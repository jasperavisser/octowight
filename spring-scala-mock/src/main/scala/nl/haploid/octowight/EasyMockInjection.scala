package nl.haploid.octowight

import java.lang.reflect.Field

trait EasyMockInjection extends MockInjection {

  object ReflectedEasyMock {
    val createMock = Class.forName("org.easymock.EasyMock").getMethod("createMock", classOf[Class[Object]])
    val createMockBuilder = Class.forName("org.easymock.EasyMock").getMethod("createMockBuilder", classOf[Class[Object]])
  }

  object ReflectedIMockBuilder {
    val createMock = Class.forName("org.easymock.IMockBuilder").getMethod("createMock")
  }

  override def createMockInstance(injectableField: Field) = {
    ReflectedEasyMock.createMock.invoke(null, injectableField.getType.asInstanceOf[Class[AnyRef]])
  }

  override def createPartialMockInstance(injectableField: Field) = {
    val builder = ReflectedEasyMock.createMockBuilder.invoke(null, injectableField.getType.asInstanceOf[Class[AnyRef]])
    ReflectedIMockBuilder.createMock.invoke(builder)
  }
}
