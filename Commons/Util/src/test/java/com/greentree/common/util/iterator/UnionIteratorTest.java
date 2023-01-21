package com.greentree.common.util.iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.greentree.commons.util.iterator.IteratorUtil;
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
	
	@Test
	void equalsToTwoIterator() {
		final var merge = IteratorUtil.union(List.of("a", "b", "c"), List.of("a", "b", "c"));
		
		final var result = new ArrayList<String>();
		for(var e : merge)
			result.add(e);
		
		assertEquals(List.of("a", "b", "c", "a", "b", "c"), result);
	}
	
	@Test
	void equalsToUnionWithEmpty() {
		final var merge = IteratorUtil.union(List.of("a", "b", "c"), List.of());
		
		final var result = new ArrayList<String>();
		for(var e : merge)
			result.add(e);
		
		assertEquals(List.of("a", "b", "c"), result);
	}
	
}
