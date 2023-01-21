package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.tests.ExecuteCounter;

public class ConstValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		final var v1 = ConstValue.newValue(TEXT);
		
		assertEquals(v1.get(), TEXT);
		
		assertTrue(v1.isConst());
		assertFalse(v1.isNull());
		
		assertTrue(v1 == v1.toConst());
		
		try(final var count = new ExecuteCounter(0)) {
			try(final var lc = v1.observer().addListener(count);) {
			}
		}
	}
	
}
