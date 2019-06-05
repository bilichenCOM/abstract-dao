package com.mateacademy.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class ReflectionUtils {

	private static final Logger logger = Logger.getLogger(ReflectionUtils.class);

	public static <T> T createNewInstanceWithProperties(Class<T> clazz, Map<String, Object> properties) {
		T t = null;
		try {
			t = clazz.newInstance();
			logger.info(String.format("new instance of %s is created...", clazz.getName()));
			Field[] fields = clazz.getDeclaredFields();
			logger.info("begin to setting fields ...");
			for (Field field:fields) {
				String fieldName = field.getName();
				Object value = properties.get(fieldName);

				// in some cases data types retrieved from database is not compatible
				// so it must be checked and casted, if necessary
				if (field.getType() != value.getClass()) {
					if (field.getType() == Double.class) {
						if (value instanceof BigDecimal) {
							BigDecimal bigDecimal = (BigDecimal) value;
							value = bigDecimal.doubleValue();
						}
					} else if (field.getType() == Character.class) {
						if (value instanceof String) {
							String string = (String) value;
							value = string.toCharArray()[0];
						}
					}
				}
				field.setAccessible(true);
				field.set(t, value);
				logger.info(String.format("field %s set to value: %s", fieldName, value));
			}
		} catch (InstantiationException e) {
			logger.debug("cannot maken an instance of " + clazz.getName() + " - perhaps no constructor without parameters");
		} catch (IllegalAccessException e) {
			logger.debug("illegal access to field");
		}
		
		return t;
	}

	public static <T> Set<String> retrieveFieldsSet(Class<T> clazz) {
		Set<String> fieldsSet = new HashSet<>();
		Field[] fields = clazz.getDeclaredFields();
		Arrays.asList(fields).forEach(f -> fieldsSet.add(f.getName()));
		return fieldsSet;
	}

	public static <T> Map<String, Object> retrieveFieldsMap(T t) {
		Map<String, Object> fieldsMap = new HashMap<>();
		@SuppressWarnings("unchecked")
		Class<T> clazz =  (Class<T>) t.getClass();
		Field[] fields = clazz.getDeclaredFields();

		Arrays.asList(fields).forEach(f -> { f.setAccessible(true);
				try {
					fieldsMap.put(f.getName(), f.get(t));
				} catch (Exception e) {
					logger.debug("problems by reading object properties..." + e.getMessage());
				}});

		return fieldsMap;
	}
}
