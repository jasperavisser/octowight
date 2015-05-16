package nl.haploid.octowight

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

import scala.util.{Failure, Success, Try}

class JsonMapException(message: String, cause: Throwable = null) extends RuntimeException(message, cause)

class JsonMapper {
  private[this] val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)

  def deserialize[T](serialized: String, targetClass: Class[T]) = {
    val result = Try(mapper.readValue(serialized, targetClass))
    result match {
      case Success(value) => value
      case Failure(e) => throw new JsonMapException(s"Could not deserialize JSON: $serialized!", e)
      case _ => throw new JsonMapException(s"Could not deserialize JSON: $serialized!")
    }
  }

  def serialize(deserialized: AnyRef) = {
    val result = Try(mapper.writeValueAsString(deserialized))
    result match {
      case Success(value) => value
      case Failure(e) => throw new JsonMapException(s"Could not serialize object of type ${deserialized.getClass.getCanonicalName}!", e)
      case _ => throw new JsonMapException(s"Could not serialize object of type ${deserialized.getClass.getCanonicalName}!")
    }
  }
}
