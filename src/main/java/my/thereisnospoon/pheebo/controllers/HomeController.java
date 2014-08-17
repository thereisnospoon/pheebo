package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.persistance.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = "/rest/{board}", method = RequestMethod.GET, headers = {"Accept=application/json"})
	@ResponseBody
	public String rest(@PathVariable String board) {

		log.debug("board " + board);

		Board boardEntity = entityManager.find(Board.class, board);
		return boardEntity != null ? boardEntity.toString() : "null";
	}
}
