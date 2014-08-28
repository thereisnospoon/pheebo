package my.thereisnospoon.pheebo.persistence.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import java.util.*;
import java.util.stream.Collectors;

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
		assertTrue(validator.validateProperty(post, "message").size() == 0);
	}

	@Test
	public void test() throws Exception {

		Thread t1 = new Thread();
		Thread t2 = new Thread();
		Date d1 = new Date();
		Date d2 = new Date();
		t1.setLastResponseDate(d2);
//		t2.setLastResponseDate(d1);
		List<Thread> t = new LinkedList<>();
		t.add(t1);
		t.add(t2);

		List<Date> d = new LinkedList<>();
		d.add(null);
		d.add(null);

		Collections.sort(t, new Thread.ThreadComparator());
	}
}
