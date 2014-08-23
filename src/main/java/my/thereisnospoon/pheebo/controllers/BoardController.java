package my.thereisnospoon.pheebo.controllers;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import my.thereisnospoon.pheebo.persistence.model.Post;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import my.thereisnospoon.pheebo.services.BoardService;
import my.thereisnospoon.pheebo.services.ThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoardController {

	public static final int THREADS_PER_PAGE = 8;
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	@Autowired
	private ThreadService threadService;

	@Autowired
	private BoardService boardService;

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

	@RequestMapping(value = "/{board}/thread", method = RequestMethod.POST)
	public String createThread(@PathVariable String board, @Valid Post post, BindingResult bindingResult,
							   @RequestParam("header") String header) {

		log.debug("Thread's header: {}", header);

		if (!bindingResult.hasErrors()) {

			Long threadId = threadService.createThread(header, post, board).getThreadId();

			log.debug("New thread id = {}", threadId);

			return "redirect:/{board}/thread/" + threadId;
		}
		return "redirect:/{board}";
	}
}
