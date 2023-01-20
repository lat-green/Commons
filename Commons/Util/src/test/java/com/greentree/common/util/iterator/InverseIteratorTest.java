package com.greentree.common.util.iterator;

import static com.greentree.common.util.iterator.IterAssertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.iterator.IteratorUtil;

public class InverseIteratorTest {

	@Test
	void test1() {
		final var list = IteratorUtil.iterator("A", "B", "C");

		final var inv = IteratorUtil.inverse(list);

		assertEqualsAsList(inv, IteratorUtil.iterator("C", "B", "A"));

	}

}
