package com.pocs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcConnectionTest {

	private static final String DB = "jdbc:postgresql://<MyDB>:5422/dev";
	private static final String DB_OPTIONS = "?readOnly=true&readOnlyMode=always";
	private static final String CREDENTIALS = "p9";
	private static final String SQL = "INSERT INTO p9.bank_logo (logo_name, image_data) values ('mylogo' , 'your_logo')";
	private static final String SQL2 = "Select now()";
	private static final String READ_ONLY = "readOnly";
	private static final String READ_ONLY_MODE = "readOnlyMode";
	private static final String TRUE = "true";
	private static final String ALWAYS = "always";

	public static void main(String[] args) {
		test2();

	}

	private static void test2(){
		Properties properties = new Properties();
		properties.setProperty(READ_ONLY, TRUE);
		properties.setProperty(READ_ONLY_MODE, ALWAYS);
		properties.setProperty("user", CREDENTIALS);
		properties.setProperty("password", CREDENTIALS);

		try(Connection conn = DriverManager.getConnection(DB, properties);
			Statement statement = conn.createStatement()) {
			statement.executeUpdate(SQL);
			//statement.execute(SQL2);
			System.out.println("aaaa");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	private static void test1(){
		try(Connection conn = DriverManager.getConnection(DB + DB_OPTIONS, CREDENTIALS, CREDENTIALS);
			Statement statement = conn.createStatement()) {
			statement.executeUpdate(SQL);
			//statement.execute(SQL2);
			System.out.println("aaaa");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
