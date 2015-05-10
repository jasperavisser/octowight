package nl.haploid.octowight

import org.codehaus.jackson.map.ObjectMapper

import scala.util.{Failure, Success, Try}

class JsonMapException(message: String, cause: Throwable) extends RuntimeException(message, cause)

class JsonMapper {
  private[this] val mapper = new ObjectMapper

  def deserialize[T](serialized: String, targetClass: Class[T]) = {
    val result = Try(mapper.readValue(serialized, targetClass))
    result match {
      case Success(value) => value
      case Failure(e) => throw new JsonMapException(s"Could not deserialize JSON: $serialized!", e)
    }
  }

  def serialize(deserialized: AnyRef) = {
    val result = Try(mapper.writeValueAsString(deserialized))
    result match {
      case Success(value) => value
      case Failure(e) => throw new JsonMapException(s"Could not serialize object of type $deserialized.getClass.getCanonicalName!", e)
    }
  }
}
