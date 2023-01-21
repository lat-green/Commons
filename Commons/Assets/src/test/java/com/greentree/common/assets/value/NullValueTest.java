package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.NullValue;
import com.greentree.commons.tests.ExecuteCounter;

public class NullValueTest {
	
	@Test
	void test_NEW() {
		final var v1 = NullValue.instance();
		
		assertNull(v1.get());
		
		assertTrue(v1.isConst());
		assertTrue(v1.isNull());
		
		assertTrue(v1 == v1.toConst());
		
		try(final var count = new ExecuteCounter(0)) {
			try(final var lc = v1.observer().addListener(count);) {
			}
		}
	}
	
}
