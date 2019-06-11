package com.mateacademy.abstract_dao.db;

public class TableNameResolver {

	public static <T> String getTableNameFor(T t) {
		return getTableNameFor(t.getClass());
	}

	public static <T> String getTableNameFor(Class<T> clazz) {
		return clazz.getName().replaceAll(".*\\.", "");
	}
}
