package com.mateacademy.abstract_dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.mateacademy.abstract_dao.utils.ResourceResolver;

public class DbConnector {

	private static final ResourceResolver resolver = new ResourceResolver();
	private static final Logger logger = Logger.getLogger(DbConnector.class);

	public static Connection getConnection() {
		String url = resolver.getDbProperties().get("database_url");
		String user = resolver.getDbProperties().get("database_user");
		String password = "";

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.debug("some problems wiht connection..." + e.getMessage());
		}
		return connection;
	}
}