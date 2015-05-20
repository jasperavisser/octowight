package nl.haploid.octowight.sample.configuration

import java.util

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebMvcConfiguration extends WebMvcConfigurerAdapter {

  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]) {
    val converter = new MappingJackson2HttpMessageConverter
    val objectMapper = new ObjectMapper with ScalaObjectMapper
    objectMapper.registerModule(DefaultScalaModule)
    objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
    converter.setObjectMapper(objectMapper)
    converters.add(converter)
    super.configureMessageConverters(converters)
  }
}
