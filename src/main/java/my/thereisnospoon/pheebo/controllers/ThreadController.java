package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.persistence.model.Post;
import my.thereisnospoon.pheebo.persistence.model.Thread;
import my.thereisnospoon.pheebo.services.JsonMapperService;
import my.thereisnospoon.pheebo.services.PostService;
import my.thereisnospoon.pheebo.services.ThreadService;
import my.thereisnospoon.pheebo.vo.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Transactional
public class ThreadController {

	private static final Logger log = LoggerFactory.getLogger(ThreadController.class);

	@Autowired
	private JsonMapperService mapperService;

	@Autowired
	private ThreadService threadService;

	@Autowired
	private PostService postService;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

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
	public String postMessage(@PathVariable Long threadId, @Valid Post post, BindingResult bindingResult, @RequestParam Long lastPostId,
							  @RequestParam(required = false) Long imageId) {

		if (!bindingResult.hasErrors()) {

			post = postService.storePost(post, threadId, imageId);
			messagingTemplate.convertAndSend("/topic/thread-" + threadId, post.getPostId());
			return mapperService.getJson(postService.getPostsAfter(lastPostId, threadId));
		} else {
			return mapperService.getJson(new ErrorVO(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
					.reduce("", (s1, s2) -> s1 + "; " + s2)));
		}
	}

	@RequestMapping(value = "/thread/{threadId}", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNewPosts(@PathVariable Long threadId, @RequestParam Long lastPostId) {
		return mapperService.getJson(postService.getPostsAfter(lastPostId, threadId));
	}
}
