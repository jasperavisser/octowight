package nl.haploid.octowight.sample.controller

import nl.haploid.octowight.sample.service.CaptainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PathVariable, RequestMapping, RequestMethod, ResponseBody}

import scala.collection.JavaConverters._

@Controller
@RequestMapping(Array("/captain"))
@ResponseBody
class CaptainController extends AbstractController {
  @Autowired private[this] val captainService: CaptainService = null

  @RequestMapping(method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def getCaptains = captainService.getAllModels.toList.asJava

  @RequestMapping(value = Array("/{id}"), method = Array(RequestMethod.GET), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  def getCaptain(@PathVariable id: Long) = captainService.getModel(id)
}
