package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.tests.ExecuteCounter;

public class MutableValueTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "B";
	
	@Test
	void copy() {
		try(final var value = new MutableValue<>(TEXT1);) {
			assertEquals(value.get(), TEXT1);
			try(final var copy = value.copy()) {
				assertEquals(copy.get(), TEXT1);
				assertEquals(value.get(), TEXT1);
				copy.set(TEXT2);
				assertEquals(copy.get(), TEXT2);
				assertEquals(value.get(), TEXT1);
			}
			try(final var copy = value.copy()) {
				assertEquals(copy.get(), TEXT1);
				assertEquals(value.get(), TEXT1);
				value.set(TEXT2);
				assertEquals(copy.get(), TEXT1);
				assertEquals(value.get(), TEXT2);
			}
		}
	}
	
	@Test
	void newValue() {
		try(final var v1 = new MutableValue<>(TEXT1);) {
			assertEquals(v1.get(), TEXT1);
			
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
					v1.set(TEXT1);
				}
			}
			
			assertEquals(v1.get(), TEXT1);
			
			try(final var count = new ExecuteCounter(1)) {
				try(final var lc = v1.observer().addListener(count);) {
					v1.set(TEXT1);
				}
			}
		}
	}
	
}
