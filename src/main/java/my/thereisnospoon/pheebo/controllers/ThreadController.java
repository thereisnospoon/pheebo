package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.persistence.model.Post;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import my.thereisnospoon.pheebo.services.JsonMapperService;
import my.thereisnospoon.pheebo.services.PostService;
import my.thereisnospoon.pheebo.services.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Transactional
public class ThreadController {

	@Autowired
	private JsonMapperService mapperService;

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

	@RequestMapping(value = "/thread/{threadId}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String postMessage(@PathVariable Long threadId, @Valid Post post, BindingResult bindingResult, @RequestParam Long lastPostId) {


		if (!bindingResult.hasErrors()) {
			postService.storePost(post, threadId);
			return mapperService.getJson(postService.getPostsAfter(lastPostId, threadId));
		}
		return "{\"error\": \"not valid\"}";
	}
}
