package com.greentree.common.util.iterator;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayIterator<E> implements SizedIterator<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private final E[] es;
	private int i;
	private final int size;

	@SafeVarargs
	public ArrayIterator(E...es) {
		this.es = es;
		this.size = es.length;
	}

	@SafeVarargs
	public ArrayIterator(int size, E...es) {
		this.es = es;
		if(size > es.length) throw new IllegalArgumentException("size > array length");
		this.size = size;
	}

	@SafeVarargs
	public ArrayIterator(int begin, int end, E...es) {
		this.es = es;
		if(end > es.length) throw new IllegalArgumentException("end > array length");
		this.i = begin;
		this.size = end;
	}
	@Override
	public boolean hasNext() {
		return i < size;
	}

	@Override
	public E next() {
		return es[i++];
	}

	@Override
	public int size() {
		return size - i;
	}

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
