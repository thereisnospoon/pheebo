package my.thereisnospoon.pheebo.services;

import org.junit.Test;
import static org.junit.Assert.*;

public class ImageServiceTest {

	@Test
	public void testComputeDigest() throws Exception {

		byte[] data = "data".getBytes();
		ImageService imageService = new ImageService();
		assertEquals("a17c9aaa61e80a1bf71d0d850af4e5baa9800bbd", imageService.computeDigest(data));
	}
}
