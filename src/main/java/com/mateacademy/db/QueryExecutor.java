package com.mateacademy.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class QueryExecutor {

	private static final Logger logger = Logger.getLogger(QueryExecutor.class);

	public static <ID> Map<String, Object> retrieve(String tableName, Set<String> columns, ID id) {
		Map<String, Object> properties = new HashMap<>();
		String sql = buildSelectQuery(tableName, columns, id);

		try (Connection connection = DbConnector.getConnection()) {

			logger.info(String.format("execute query [%s]", sql));
			ResultSet resultSet = connection.createStatement().executeQuery(sql);

			if (resultSet.next()) {
				for (String column : columns) {
					properties.put(column, resultSet.getObject(column));
				}
			} else {
				logger.info("row with id " + id + " doesn't exists...");
				return null;
			}
		} catch (SQLException e) {
			logger.debug(
					"problems by retrieving from table: " + tableName + " with id: " + id + "..." + e.getMessage());
		}
		return properties;
	}

	public static void insertIntoTable(String tableName, Map<String, Object> properties) {
		String sql = buildInsertQuery(tableName, properties);
		try {
			executeSql(sql);
		} catch (SQLException e) {
			logger.debug("problems by inserting into table: " + tableName + "..." + e.getMessage());
		}
	}

	public static void createTableIfNotExists(String tableName, Map<String, Object> properties) {
		String sql = buildCreateTableSqlQuery(tableName, properties);
		try {
			executeSql(sql);
		} catch (SQLException e) {
			logger.debug("problems by creating table: " + tableName + "..." + e.getMessage());
		}
	}

	private static <ID> String buildSelectQuery(String tableName, Set<String> columns, ID id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		columns.stream().forEach(c -> sql.append(c + ", "));
		sql.delete(sql.length() - 2, sql.length());
		sql.append(String.format(" FROM %s ", tableName));
		sql.append("WHERE id=");

		if (SqlDataTypeResolver.isQuotesNeeded(id)) {
			sql.append(String.format("'%s';", id));
		} else {
			sql.append(String.format("%s;", id));
		}
		return sql.toString();
	}

	private static String buildInsertQuery(String tableName, Map<String, Object> properties) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		sql.append(tableName + " (");
		properties.keySet().stream().forEach(k -> sql.append(k + ", "));
		sql.delete(sql.length() - 2, sql.length());
		sql.append(" ) ");
		sql.append("VALUES (");

		for (String key : properties.keySet()) {
			Object value = properties.get(key);

			if (SqlDataTypeResolver.isQuotesNeeded(value)) {
				sql.append(String.format("'%s', ", value));
			} else {
				sql.append(String.format("%s, ", value));
			}
		}

		sql.delete(sql.length() - 2, sql.length());
		sql.append(" );");
		return sql.toString();
	}

	private static String buildCreateTableSqlQuery(String tableName, Map<String, Object> properties) {
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE IF NOT EXISTS ");
		sql.append(tableName + " (");

		if (!properties.containsKey("id")) {
			sql.append("ID BIGINT PRIMARY KEY, ");
		}

		for (String column : properties.keySet()) {
			String sqlDataType = SqlDataTypeResolver.resolveSqlTypeFor(properties.get(column)).orElseThrow(
					() -> new IllegalArgumentException("unexpected data type for column:" + column.toUpperCase()));
			if (column.equals("id")) {
				sql.append(String.format("%s %s PRIMARY KEY, ", column, sqlDataType));
			} else {
				sql.append(String.format("%s %s, ", column, sqlDataType));
			}
		}
		sql.delete(sql.length() - 2, sql.length());
		sql.append(");");

		return sql.toString();
	}

	private static void executeSql(String sql) throws SQLException {
		Connection connection = DbConnector.getConnection();
		connection.createStatement().execute(sql);
		logger.debug("query: [" + sql + "] executed");
		connection.close();
	}
}
