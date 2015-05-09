package nl.haploid.octowight.sample.controller

import nl.haploid.octowight.sample.data.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ExceptionHandler, ResponseStatus}

class AbstractController {

  @ExceptionHandler(Array(classOf[ResourceNotFoundException]))
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def handleResourceNotFoundException = "Resource not found"
}
