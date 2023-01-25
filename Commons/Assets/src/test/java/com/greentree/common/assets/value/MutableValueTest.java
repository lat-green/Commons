package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.MutableSource;

public class MutableValueTest {
	
	private static final String TEXT = "A";
	
	@Test
	void test_NEW() {
		final var v1 = new MutableSource<>(TEXT);
		try(final var p = v1.openProvider()) {
			assertEquals(p.get(), TEXT);
			
			assertFalse(v1.hasConst());
			assertFalse(v1.isNull());
			
			v1.set(null);
			
			assertTrue(v1.isNull());
			assertNull(p.get());
		}
	}
}
