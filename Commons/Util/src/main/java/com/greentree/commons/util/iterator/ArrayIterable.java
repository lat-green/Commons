package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayIterable<E> implements SizedIterable<E>, Serializable {

	private static final long serialVersionUID = 1L;

	private final int begin, end;
	private final E[] es;

	/**
	 * Creates iterable over all elements.
	 *
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterable(E... es) {
		this(0, es.length, es);
	}

	/**
	 * Creates iterable over first size elements.
	 *
	 * @param size number of elements
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterable(int size, E... es) {
		this(0, size, es);
	}

	/**
	 * Creates iterable from begin (inclusive) to end (exclusive).
	 *
	 * @param begin start index (inclusive)
	 * @param end end index (exclusive)
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterable(int begin, int end, E... es) {
		if(end > es.length)
			throw new IllegalArgumentException("end > array length");
		if(begin < 0)
			throw new IllegalArgumentException("begin < 0");
		if(end < begin)
			throw new IllegalArgumentException("begin > end");
		this.es = es;
		this.begin = begin;
		this.end = end;
	}

	/**
	 * Returns iterator over the array.
	 *
	 * @return array iterator
	 */
	@Override
	public ArrayIterator<E> iterator() {
		return new ArrayIterator<>(begin, end, es);
	}

	/**
	 * Returns copy of underlying array.
	 *
	 * @return copy of array
	 */
	public E[] toArray() {
		return Arrays.copyOf(es, es.length);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArrayIterable [");
		final var iter = iterator();
		if(iter.hasNext())
			builder.append(iter.next());
		while(iter.hasNext()) {
			builder.append(", ");
			builder.append(iter.next());
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Returns number of elements.
	 *
	 * @return element count
	 */
	@Override
	public int size() {
		return end - begin;
	}

}
