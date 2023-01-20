package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.iterator.ArrayIterable;

public class ArrayIterableTest {

	@Test
	void testSize() {
		assertEquals(new ArrayIterable<>("A", "B").size(), 2);
	}
	
}
