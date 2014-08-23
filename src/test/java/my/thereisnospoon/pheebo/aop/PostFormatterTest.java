package my.thereisnospoon.pheebo.aop;

import my.thereisnospoon.pheebo.persistence.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.filter.CharacterEncodingFilter;

public class PostFormatterTest {

	@Test
	public void testReplaceBreaks() {

		PostFormatter postFormatter = new PostFormatter();
		Post post = new Post();
		post.setMessage("1\n" + "2");

		postFormatter.replaceBreaks(post);
		Assert.assertEquals("1<br/>2", post.getMessage());
	}

	@Test
	public void testReplaceSpaces() {

		PostFormatter postFormatter = new PostFormatter();
		Post post = new Post();
		post.setMessage("1   2");
		postFormatter.replaceBreaks(post);
		Assert.assertEquals("1&nbsp&nbsp&nbsp2", post.getMessage());
	}
}