package com.mateacademy.abstract_dao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class ResourceResolver {

	private static final String DB_PROPERTIES_FILE_NAME = "db.properties";

	private static final Logger logger = Logger.getLogger(ResourceResolver.class);

	public Map<String, String> getDbProperties() {
		return getPropertiesFromFile(DB_PROPERTIES_FILE_NAME);
	}

	public Map<String, String> getPropertiesFromFile(String resourceFileName) {
		Map<String, String> properties = new HashMap<>();

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStreamFromResourceFile(resourceFileName)));

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] entry = line.split("=");
				String key = entry[0];
				String value = entry[1];
				properties.put(key, value);
			}
		} catch (IOException e) {
			logger.debug("problems with reading file from resource folder..." + e.getMessage());
		}
		return properties;
	}

	private InputStream getInputStreamFromResourceFile(String resourceFileName) {
		return getClass()
				.getClassLoader()
				.getResourceAsStream(resourceFileName);
	}
}
