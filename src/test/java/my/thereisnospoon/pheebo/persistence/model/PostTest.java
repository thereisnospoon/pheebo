package my.thereisnospoon.pheebo.persistence.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class PostTest {

	private static Validator validator;

	@BeforeClass
	public static void init() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void emptyMessage() {

		Post post = new Post();
		post.setMessage("");
		assertTrue(validator.validateProperty(post, "message").size() > 0);
	}

	@Test
	public void testJSON() throws Exception {

		Post post = new Post();
		post.setMessage("Message in post");
		post.setPostedWhen(new Date());
		post.setThread(new Thread());

		ObjectMapper mapper = new ObjectMapper();

		System.out.println(mapper.writeValueAsString(post));

		Post post2 = new Post();
		post2.setMessage("Message 2");

		System.out.println(mapper.writeValueAsString(Arrays.asList(post, post2)));
	}
}
