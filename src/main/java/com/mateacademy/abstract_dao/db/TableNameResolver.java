package com.mateacademy.abstract_dao.db;

import com.mateacademy.abstract_dao.annotations.Tabelle;

public class TableNameResolver {

	public static <T> String getTableNameFor(T t) {
		return checkAnnotationForTableName(t.getClass());
	}

	public static <T> String getTableNameFor(Class<T> clazz) {
		return checkAnnotationForTableName(clazz);
	}

	private static <T> String checkAnnotationForTableName(Class<T> clazz) {
		Tabelle[] tabelleAnnotations = clazz.getAnnotationsByType(Tabelle.class);
		if (tabelleAnnotations.length == 0) {
			throw new IllegalArgumentException("type can not be persisted. Annotation Tabelle is omitted");
		}
		return tabelleAnnotations[0].value();
	}
}