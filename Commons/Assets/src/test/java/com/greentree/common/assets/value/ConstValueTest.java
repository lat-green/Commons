package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.ConstSource;

public class ConstValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		final var v1 = ConstSource.newValue(TEXT);
		
		assertEquals(v1.get(), TEXT);
		
		assertTrue(v1.hasConst());
		assertFalse(v1.isNull());
	}
	
}
