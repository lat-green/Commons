package com.greentree.common.assets.value;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.assets.source.NullSource;

public class NullValueTest {
	
	@Test
	void test_NEW() {
		final var v1 = NullSource.instance();
		
		assertNull(v1.get());
		
		assertTrue(v1.hasConst());
		assertTrue(v1.isNull());
	}
	
}
