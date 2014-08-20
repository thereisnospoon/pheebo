package my.thereisnospoon.pheebo.controllers;

import com.google.common.collect.Lists;
import my.thereisnospoon.pheebo.persistance.model.Post;
import my.thereisnospoon.pheebo.services.ThreadService;
import my.thereisnospoon.pheebo.persistance.model.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoardController {

	public static final int THREADS_PER_PAGE = 15;

	@Autowired
	private ThreadService threadService;

	@RequestMapping(value = "/{board}", method = RequestMethod.GET)
	public String showBoard(@PathVariable String board, Model model) {

		return showBoardPage(board, 1, model);
	}

	@RequestMapping(value = "/{board}/page/{page}", method = RequestMethod.GET)
	public String showBoardPage(@PathVariable String board, @PathVariable int page, Model model) {

		List<List<Thread>> pages = Lists.partition(threadService.getBoardsThreads(board), THREADS_PER_PAGE);

		if (pages.size() > page) {

			List<List<Post>> threadPreviews = pages.get(page - 1).stream()
					.map(
							(thread) -> threadService.getThreadPreview(thread.getThreadId()))
					.collect(Collectors.toList());

			model.addAttribute("threadPreviews", threadPreviews);
			model.addAttribute("page", page);

			return "board";
		}

		model.addAttribute("message", "404: Not found");
		return "error";
	}
}
