package com.mateacademy.db;

public class TableNameResolver {

	public static <T> String getTableNameFor(T t) {
		return t.getClass().getName().replaceAll(".*\\.", "");
	}

	public static <T> String getTableNameFor(Class<T> clazz) {
		return clazz.getName().replaceAll(".*\\.", "");
	}
}
