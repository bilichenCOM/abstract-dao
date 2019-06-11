package com.mateacademy.abstract_dao.utils;

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
				Object value = validateValue(field, properties.get(fieldName));
				field.setAccessible(true);
				field.set(t, value);
				logger.info(String.format("field %s set to value: %s", fieldName, value));
			}
		} catch (InstantiationException e) {
			logger.debug("cannot make an instance of " + clazz.getName() + " - perhaps no constructor without parameters");
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

	@SuppressWarnings("unchecked")
	private static <T> T validateValue(Field field, Object value) {
		if (field.getType() != value.getClass()) {
			if (field.getType() == Double.class) {
				if (value instanceof BigDecimal) {
					BigDecimal bigDecimalValue = (BigDecimal) value;
					value = bigDecimalValue.doubleValue();
				} else if (value instanceof Float) {
					Float floatValue = (Float) value;
					value = floatValue.doubleValue();
				}
			} else if (field.getType() == Character.class) {
				if (value instanceof String) {
					String string = (String) value;
					value = string.toCharArray()[0];
				}
			}
		}
		return (T) value;
	}
}