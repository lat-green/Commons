package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Arrays;

public class ArrayIterable<E> implements SizedIterable<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final int begin, end;
	private final E[] es;
	
	@SafeVarargs
	public ArrayIterable(E... es) {
		this(0, es.length, es);
	}
	
	@SafeVarargs
	public ArrayIterable(int size, E... es) {
		this(0, size, es);
	}
	
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
	
	@Override
	public ArrayIterator<E> iterator() {
		return new ArrayIterator<>(begin, end, es);
	}
	
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
	
	@Override
	public int size() {
		return end - begin;
	}
	
}
