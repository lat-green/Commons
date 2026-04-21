package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayIterator<E> implements SizedIterator<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private final E[] es;
	private int i;
	private final int size;

	/**
	 * Creates iterator over all elements.
	 *
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterator(E...es) {
		this.es = es;
		this.size = es.length;
	}

	/**
	 * Creates iterator over first size elements.
	 *
	 * @param size number of elements to iterate
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterator(int size, E...es) {
		this.es = es;
		if(size > es.length) throw new IllegalArgumentException("size > array length");
		this.size = size;
	}

	/**
	 * Creates iterator from begin (inclusive) to end (exclusive).
	 *
	 * @param begin start index (inclusive)
	 * @param end end index (exclusive)
	 * @param es elements to iterate
	 */
	@SafeVarargs
	public ArrayIterator(int begin, int end, E...es) {
		this.es = es;
		if(end > es.length) throw new IllegalArgumentException("end > array length");
		this.i = begin;
		this.size = end;
	}

	/**
	 * Returns true if more elements remain.
	 *
	 * @return true if has more elements
	 */
	@Override
	public boolean hasNext() {
		return i < size;
	}

	/**
	 * Returns next element and advances iterator.
	 *
	 * @return next element
	 */
	@Override
	public E next() {
		return es[i++];
	}

	/**
	 * Returns number of remaining elements.
	 *
	 * @return remaining elements count
	 */
	@Override
	public int size() {
		return size - i;
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
		builder.append("ArrayIterator [es=");
		builder.append(Arrays.toString(es));
		builder.append(", size=");
		builder.append(size);
		builder.append(", i=");
		builder.append(i);
		builder.append("]");
		return builder.toString();
	}


}
