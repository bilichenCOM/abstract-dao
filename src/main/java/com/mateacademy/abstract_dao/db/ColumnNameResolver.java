package com.mateacademy.abstract_dao.db;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mateacademy.abstract_dao.annotations.Spalte;

public class ColumnNameResolver {

	private static final Logger logger = Logger.getLogger(ColumnNameResolver.class);

	public static <T> Map<String, Object> resolveColumnNamesFor(Map<String, Object> properties,
			Class<T> clazz) {
		Map<String, Object> resolvedProperties = new HashMap<>();

		for (String fieldName : properties.keySet()) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				if (field.isAnnotationPresent(Spalte.class)) {
					Spalte columnAnnotation = field.getAnnotation(Spalte.class);
					resolvedProperties.put(columnAnnotation.value(), properties.get(fieldName));
				} else {
					resolvedProperties.put(fieldName, properties.get(fieldName));
				}
			} catch (NoSuchFieldException e) {
				logger.debug(String.format("field %s not founded for class %s", fieldName, clazz.getSimpleName()));
			} catch (SecurityException e) {
				logger.debug("security issues by column name resolving" + e.getMessage());
			}
		}
		return resolvedProperties;
	}

	public static <T> Map<String, Object> resolveFieldNamesFor(Map<String, Object> properties, Class<T> clazz) {
		Map<String, Object> resolvedFieldsMap = new HashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Spalte.class)) {
				Spalte columnAnnotation = field.getAnnotation(Spalte.class);
				resolvedFieldsMap.put(field.getName(), properties.get(columnAnnotation.value()));
			} else {
				resolvedFieldsMap.put(field.getName(), properties.get(field.getName()));
			}
		}
		return resolvedFieldsMap;
	}

	public static <T> Set<String> resolveColumnNamesFor(Set<String> fieldSet, Class<T> clazz) {
		Set<String> resolvedSet = new HashSet<>();
		for (String fieldName : fieldSet) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				if (field.isAnnotationPresent(Spalte.class)) {
					resolvedSet.add(field.getAnnotation(Spalte.class).value());
				} else {
					resolvedSet.add(fieldName);
				}
			} catch (NoSuchFieldException e) {
				logger.debug(String.format("field %s not found for class %s", fieldName, clazz.getSimpleName()));
			} catch (SecurityException e) {
				logger.debug("security issues by resolving column names from field set");
			}
		}
		return resolvedSet;
	}
}