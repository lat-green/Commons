package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.tests.ExecuteCounter;

public class MutableValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		try(final var v1 = new MutableValue<>(TEXT);) {
			assertEquals(v1.get(), TEXT);
			
			assertFalse(v1.isConst());
			assertFalse(v1.isNull());
			
			v1.set(null);
			
			assertTrue(v1.isNull());
			assertNull(v1.get());
			
			try(final var count = new ExecuteCounter(0)) {
				try(final var lc = v1.observer().addListener(count);) {
				}
			}
			
			try(final var count = new ExecuteCounter(1)) {
				try(final var lc = v1.observer().addListener(count);) {
					v1.set(TEXT);
				}
			}
			
			assertEquals(v1.get(), TEXT);
			
			try(final var count = new ExecuteCounter(1)) {
				try(final var lc = v1.observer().addListener(count);) {
					v1.set(TEXT);
				}
			}
		}
	}
	
}
