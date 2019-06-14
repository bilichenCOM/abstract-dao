package com.mateacademy.abstract_dao.test.utils;

import org.junit.Assert;
import org.junit.Test;

import com.mateacademy.abstract_dao.utils.ResourceResolver;

public class ResourceResolverTest {

	private static final ResourceResolver resolver = new ResourceResolver();

	@Test
	public void testGetDbProperties() {
		resolver.getDbProperties().forEach((k, v) -> System.out.println(k + " " + v));
		Assert.assertEquals(1, 1);
	}
}