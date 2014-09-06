package my.thereisnospoon.pheebo.aop;

import my.thereisnospoon.pheebo.persistence.model.Post;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class PostFormatterTest {

	@Test
	public void testReplaceBreaks() {

		PostFormatter postFormatter = new PostFormatter();
		Post post = new Post();
		post.setMessage("1\n" + "2");

		postFormatter.replaceBreaks(post);
		Assert.assertEquals("1<br/>2", post.getMessage());
	}

	@Ignore
	@Test
	public void testReplaceSpaces() {

		PostFormatter postFormatter = new PostFormatter();
		Post post = new Post();
		post.setMessage("1   2");
		postFormatter.replaceBreaks(post);
	}
}