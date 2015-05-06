package nl.haploid.octowight

import java.lang.reflect.Field

trait EasyMockInjection extends MockInjection {
  private val method = Class.forName("org.easymock.EasyMock").getMethod("createMock", classOf[Class[Object]])

  override def createMockInstance(injectableField: Field): AnyRef = {
    method.invoke(null, injectableField.getType.asInstanceOf[Class[AnyRef]])
  }
}
