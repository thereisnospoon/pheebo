package my.thereisnospoon.pheebo.controllers;

import my.thereisnospoon.pheebo.services.ThreadService;
import my.thereisnospoon.pheebo.persistance.model.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThreadController {

	@Autowired
	private ThreadService threadService;

	@RequestMapping("/{board}/thread/{threadId}")
	public String showThread(@PathVariable String board, @PathVariable Long threadId, Model model) {

		Thread thread = threadService.getThread(threadId);
		model.addAttribute("thread", thread);

		return "thread";
	}
}
