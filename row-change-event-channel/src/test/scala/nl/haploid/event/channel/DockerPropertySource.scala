package nl.haploid.event.channel

import org.springframework.core.env.PropertySource

class DockerPropertySource extends PropertySource("docker") {

  // TODO: bollocks, I don't think we can autowire the container here;
  // TODO: unless we autowire this source into the TestConfiguration that implements the Configurer
  // TODO: meh

  override def getProperty(name: String): AnyRef = {
    return null
  }
}
