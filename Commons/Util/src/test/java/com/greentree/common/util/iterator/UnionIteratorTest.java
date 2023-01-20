package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.iterator.MergeIterator;

public class UnionIteratorTest {

	private static <E> void assertEqualsIterator(Iterator<E> iter1, Iterator<E> iter2) {
		final boolean hasNext;
		hasNext = iter1.hasNext();

		assertEquals(hasNext, iter2.hasNext());

		if(hasNext) {
			final var n1 = iter1.next();
			final var n2 = iter2.next();
			assertEquals(n1, n2);
			assertEqualsIterator(iter1, iter2);
		}
	}

	@Test
	void equalsToOneIterator() {
		final var list = new ArrayList<String>();

		list.add("a");
		list.add("b");
		list.add("c");

		final var filterIter = new MergeIterator<>(list.iterator());

		assertEqualsIterator(filterIter, list.iterator());

	}

}
