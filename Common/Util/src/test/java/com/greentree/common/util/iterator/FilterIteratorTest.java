package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.greentree.common.util.iterator.EmptyIterator;
import com.greentree.common.util.iterator.FilterIterator;

public class FilterIteratorTest {



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
	void falsePredicateArrayList() {
		final var list = new ArrayList<String>();

		list.add("a");
		list.add("b");
		list.add("c");

		final var filterIter = new FilterIterator<>(list.iterator(), e -> false);

		assertEqualsIterator(filterIter, EmptyIterator.instance());

	}

	@Test
	void filterPredicate() {
		final var list = new ArrayList<String>();
		final var res = new ArrayList<String>();

		list.add("a");
		list.add("b");
		list.add("c");

		res.addAll(list);

		list.add("A");
		list.add("B");
		list.add("C");

		final var filterIter = new FilterIterator<>(list.iterator(), str -> str.toLowerCase().equals(str));

		assertEqualsIterator(filterIter, res.iterator());

	}

	@Test
	void truePredicateArrayList() {
		final var list = new ArrayList<String>();

		list.add("a");
		list.add("b");
		list.add("c");

		final var iter = list.iterator();
		final var filterIter = new FilterIterator<>(list.iterator(), e -> true);



		assertEqualsIterator(filterIter, iter);

	}
	@Test
	void truePredicateOfEmptyIterator() {
		final var iter = EmptyIterator.instance();

		final var filterIter = new FilterIterator<>(iter, e -> true);

		assertEqualsIterator(filterIter, EmptyIterator.instance());

	}

}
