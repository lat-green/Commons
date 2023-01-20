package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.iterator.IteratorUtil;

public class MinElementTest {

	@Test
	void test1() {
		final var iter = ints(5, 6, 7);
		final var mn = IteratorUtil.min(iter);
		assertEquals(mn, 5);
	}

	@Test
	void test2() {
		final var iter = ints(5, 6, 7);
		final var mn = IteratorUtil.min(iter, i -> -i);
		assertEquals(mn, 7);
	}

	@Test
	void test3() {
		final var iter = ints(5, 6, 7);
		final var mn = IteratorUtil.min(iter, (a, b) -> a-b);
		assertEquals(mn, 5);
	}
	
	@Test
	void test4() {
		final var iter = ints(5, 6, 7);
		final var mn = IteratorUtil.min(iter, (a, b) -> b-a);
		assertEquals(mn, 7);
	}

	private Iterable<Integer> ints(Integer...arr) {
		return IteratorUtil.iterable(arr);
	}
	
}
