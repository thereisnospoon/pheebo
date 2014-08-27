package my.thereisnospoon.pheebo.controllers;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ImageControllerTest {

	@Ignore
	@Test
	public void test() throws Exception {

		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/pheebo", "adminnp9jni9", "gs7T5ELJQdqT");

		Statement statement = connection.createStatement();

		ResultSet resultSet = statement.executeQuery("select data from imgboard.images where image_id = 3");
		while (resultSet.next()) {

			System.out.println(IOUtils.toByteArray(resultSet.getBinaryStream(1)).length);
		}
		connection.close();
	}
}
