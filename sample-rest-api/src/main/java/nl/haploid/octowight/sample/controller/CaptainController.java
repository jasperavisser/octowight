package nl.haploid.octowight.sample.controller;

import nl.haploid.octowight.sample.data.CaptainModel;
import nl.haploid.octowight.sample.service.CaptainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/captain")
@ResponseBody
public class CaptainController extends AbstractController {

	@Autowired
	private CaptainService captainService;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CaptainModel> getCaptains() {
		return captainService.getAllModels();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CaptainModel getCaptain(final @PathVariable long id) {
		return captainService.getModel(id);
	}
}
