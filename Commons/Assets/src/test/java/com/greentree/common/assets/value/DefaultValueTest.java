package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.ConstSource;
import com.greentree.commons.assets.source.DefaultSource;
import com.greentree.commons.assets.source.MutableSource;
import com.greentree.commons.assets.source.NullSource;

public class DefaultValueTest {
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "B";
	
	@Test
	void test_NEW() {
		final var v1 = new MutableSource<>(TEXT1);
		final var v2 = new MutableSource<>(TEXT2);
		
		final var m = DefaultSource.newValue(v1, v2);
		
		assertFalse(m.hasConst());
		assertFalse(m.isNull());
		
		try(final var p = m.openProvider()) {
			assertEquals(p.get(), TEXT1);
			v1.set(null);
			assertEquals(p.get(), TEXT2);
			v1.set(TEXT1);
			assertEquals(p.get(), TEXT1);
		}
	}
	
	@Test
	void test_NEW_of_ONE() {
		final var v1 = new MutableSource<>(TEXT1);
		
		final var m = DefaultSource.newValue(v1);
		
		assertTrue(m == v1);
	}
	
	@Test
	void test_NEW_of_ONE_and_NULL() {
		final var v1 = new MutableSource<>(TEXT1);
		final var v2 = NullSource.<String>instance();
		
		final var m = DefaultSource.newValue(v1, v2);
		
		assertTrue(m == v1);
	}
	
	@Test
	void test_NEW_of_CONST_and_MUTABLE() {
		final var v1 = ConstSource.newValue(TEXT1);
		final var v2 = new MutableSource<>(TEXT1);
		
		final var m = DefaultSource.newValue(v1, v2);
		
		assertTrue(m == v1);
	}
	
}
