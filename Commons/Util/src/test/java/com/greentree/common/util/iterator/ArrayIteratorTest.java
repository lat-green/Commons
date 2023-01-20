package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.iterator.ArrayIterator;

public class ArrayIteratorTest {

	@Test
	void testSize() {
		assertEquals(new ArrayIterator<>("A", "B").size(), 2);
		final var iter = new ArrayIterator<>("A", "B");
		iter.next();
		assertEquals(iter.size(), 1);
	}
	
}
