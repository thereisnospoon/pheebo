package my.thereisnospoon.pheebo.persistence.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardTest {

	private static Validator validator;

	@BeforeClass
	public static void init() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void ifNoPath() {
		assertTrue(validator.validateProperty(new Board(), "path").size() == 1);
	}

	@Test
	public void ifPathPresent() {

		Board board = new Board();
		board.setPath("b");
		assertTrue(0 == validator.validate(board).size());

		board.setPath("?");
		assertEquals(1, validator.validateProperty(board, "path").size());

		board.setPath("123456789012345678901");
		assertEquals(1, validator.validateProperty(board, "path").size());
	}

	@Test
	public void exceedDescSize() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 150; i++) {
			sb.append("s");
		}
		Board board = new Board();
		board.setDescription(sb.toString());

		assertEquals(1, validator.validateProperty(board, "description").size());
	}
}
