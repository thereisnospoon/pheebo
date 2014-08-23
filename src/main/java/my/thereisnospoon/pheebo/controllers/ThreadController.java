package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.persistence.model.Post;
import my.thereisnospoon.pheebo.services.PostService;
import my.thereisnospoon.pheebo.services.ThreadService;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@Transactional
public class ThreadController {

	@Autowired
	private ThreadService threadService;

	@Autowired
	private PostService postService;

	@RequestMapping("/{board}/thread/{threadId}")
	public String showThread(@PathVariable String board, @PathVariable Long threadId, Model model) {

		Thread thread = threadService.getThread(threadId);
		//eager fetch posts
		thread.getPosts().first();
		model.addAttribute("thread", thread);
		model.addAttribute("post", new Post());

		return "thread";
	}

	@RequestMapping(value = "/{board}/thread/{threadId}", method = RequestMethod.POST)
	public String postMessage(@PathVariable Long threadId, @Valid Post post, BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			postService.storePost(post, threadId);
		}
		return "redirect:/{board}/thread/{threadId}";
	}
}
