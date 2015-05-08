package nl.haploid.octowight

import java.io.IOException

import org.codehaus.jackson.map.ObjectMapper

class JsonMapException(message: String, cause: Throwable) extends RuntimeException(message, cause)

class JsonMapper {
  private final val mapper = new ObjectMapper

  def deserialize[T](serialized: String, targetClass: Class[T]) = {
    try {
      mapper.readValue(serialized, targetClass)
    } catch {
      case e: IOException =>
        throw new JsonMapException(s"Could not deserialize JSON: $serialized!", e)
    }
  }

  def serialize(deserialized: AnyRef) = {
    try {
      mapper.writeValueAsString(deserialized)
    } catch {
      case e: IOException =>
        throw new JsonMapException(s"Could not serialize object of type $deserialized.getClass.getCanonicalName!", e)
    }
  }
}
