package my.thereisnospoon.pheebo.aop;

import my.thereisnospoon.pheebo.persistence.model.Post;
import org.apache.commons.lang3.StringEscapeUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Aspect
public class PostFormatter {

	private static final Logger log = LoggerFactory.getLogger(PostFormatter.class);

	/**
	 * Replaces line break characters with html ones and escapes html.
	 * @param post intercepted post which was passing to controller
	 */
	@Before(value = "execution(* my.thereisnospoon.pheebo.controllers.*.*(..)) && args(*, post,..)", argNames = "post")
	public void replaceBreaks(Post post) {

		log.debug("Controller received post with message {}", post.getMessage());

		String message = post.getMessage();
		message = StringEscapeUtils.escapeHtml4(message);
		message = Pattern.compile("\n", Pattern.DOTALL).matcher(message).replaceAll("<br/>");
		message = Pattern.compile("[ \t]", Pattern.DOTALL).matcher(message).replaceAll("&nbsp;");

		log.debug("Formatted message: {}", message);

		post.setMessage(message);
	}

}
