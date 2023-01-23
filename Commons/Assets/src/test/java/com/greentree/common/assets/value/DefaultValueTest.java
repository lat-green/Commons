package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.value.ConstValue;
import com.greentree.commons.assets.value.DefaultValue;
import com.greentree.commons.assets.value.MutableValue;
import com.greentree.commons.assets.value.NullValue;
import com.greentree.commons.tests.ExecuteCounter;

public class DefaultValueTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "B";
	
	@Test
	void test_NEW() {
		final var v1 = new MutableValue<>(TEXT1);
		final var v2 = new MutableValue<>(TEXT2);
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertFalse(m.isConst());
		assertFalse(m.isNull());
		
		assertEquals(m.get(), TEXT1);
		v1.set(null);
		assertEquals(m.get(), TEXT2);
		v1.set(TEXT1);
		
		try(final var count = new ExecuteCounter(0)) {
			try(final var lc = m.observer().addListener(count);) {
				v2.set(null);
				v2.set(TEXT2);
			}
		}
		
		try(final var count = new ExecuteCounter(1)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(null);
			}
		}
		
		assertEquals(m.get(), TEXT2);
		
		try(final var count = new ExecuteCounter(1)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(TEXT1);
			}
		}
		
		assertEquals(m.get(), TEXT1);
		
		try(final var count = new ExecuteCounter(0)) {
			try(final var lc = m.observer().addListener(count);) {
				v1.set(TEXT1);
				v2.set(TEXT2);
			}
		}
	}
	
	@Test
	void test_NEW_of_ONE() {
		final var v1 = new MutableValue<>(TEXT1);
		
		final var m = DefaultValue.newValue(v1);
		
		assertTrue(m == v1);
	}
	
	@Test
	void test_NEW_of_ONE_and_NULL() {
		final var v1 = new MutableValue<>(TEXT1);
		final var v2 = NullValue.<String>instance();
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertTrue(m == v1);
	}
	
	@Test
	void test_NEW_of_CONST_and_MUTABLE() {
		final var v1 = ConstValue.newValue(TEXT1);
		final var v2 = new MutableValue<>(TEXT1);
		
		final var m = DefaultValue.newValue(v1, v2);
		
		assertTrue(m == v1);
	}
	
}
