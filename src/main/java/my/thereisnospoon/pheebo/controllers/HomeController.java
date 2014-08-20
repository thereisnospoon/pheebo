package my.thereisnospoon.pheebo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String home() {
		return "index";
	}
}
