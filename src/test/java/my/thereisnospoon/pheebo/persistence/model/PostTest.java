package my.thereisnospoon.pheebo.persistence.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

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
}
