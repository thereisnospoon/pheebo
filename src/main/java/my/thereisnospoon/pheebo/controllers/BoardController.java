package my.thereisnospoon.pheebo.controllers;

import com.google.common.collect.Lists;
import my.thereisnospoon.pheebo.persistence.model.Post;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import my.thereisnospoon.pheebo.services.BoardService;
import my.thereisnospoon.pheebo.services.JsonMapperService;
import my.thereisnospoon.pheebo.services.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoardController {

	public static final int THREADS_PER_PAGE = 8;
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@Autowired
	private JsonMapperService mapperService;

	@Autowired
	private ThreadService threadService;

	@Autowired
	private BoardService boardService;

	@RequestMapping("/favicon.ico")
	@ResponseBody
	public byte[] stub() {
		return null;
	}

	@RequestMapping(value = "/{board}", method = RequestMethod.GET)
	public String showBoard(@PathVariable String board, Model model) {

		return showBoardPage(board, 1, model);
	}

	@RequestMapping(value = "/{board}/page/{page}", method = RequestMethod.GET)
	public String showBoardPage(@PathVariable String board, @PathVariable int page, Model model) {

		List<List<Thread>> pages = Lists.partition(threadService.getBoardsThreads(board), THREADS_PER_PAGE);

		log.debug("Board {} threads: {}", board, pages);

		List<List<Post>> threadPreviews;

		if (pages.size() != 0) {

			threadPreviews = pages.get(page - 1).stream()
					.map(
							(thread) -> threadService.getThreadPreview(thread.getThreadId()))
					.collect(Collectors.toList());
		} else {
			threadPreviews = Collections.emptyList();
		}

		model.addAttribute("post", new Post());
		model.addAttribute("threadPreviews", threadPreviews);
		model.addAttribute("page", page);
		model.addAttribute("totalPages", pages.size() == 0 ? 1 : pages.size());
		model.addAttribute("board", boardService.getBoard(board));

		return "board";
	}

	@RequestMapping(value = "/{board}/thread", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createThread(@PathVariable String board, @Valid Post post, BindingResult bindingResult,
							   @RequestParam("header") String header, @RequestParam(value = "imageId", required = false) Long imageId) {

		log.debug("Thread's header: {}", header);

		if (!bindingResult.hasErrors()) {

			Thread thread = threadService.createThread(header, post, board, imageId);

			log.debug("New thread id = {}", thread.getThreadId());

			return mapperService.getJson(thread);
		}
		return "{\"error\": \"something went wrong\"}";
	}
}
