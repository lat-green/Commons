package com.greentree.common.util.iterator;

import static com.greentree.common.util.iterator.IterAssertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.iterator.IteratorUtil;

public class CrossIteratorTest {

	@Test
	void test1() {
		final var i1 = IteratorUtil.iterable("A", "C", "D", "E");
		final var i2 = IteratorUtil.iterable("A", "B", "D");

		final var r = IteratorUtil.cross(i1, i2);

		final var a = IteratorUtil.iterable("A", "D");

		assertEqualsAsSet(r, a);
	}

}
