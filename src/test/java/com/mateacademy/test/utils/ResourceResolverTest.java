package com.mateacademy.test.utils;
import static org.junit.Assert.*;

import org.junit.Test;

import com.mateacademy.utils.ResourceResolver;

public class ResourceResolverTest {

	private static final ResourceResolver resolver = new ResourceResolver();
	@Test
	public void testGetDbProperties() {
		resolver.getDbProperties().forEach((k, v) -> System.out.println(k + " " + v));
		assertEquals(1, 1);
	}

	@Test
	public void testGetPropertiesFromFile() {
	}

}
