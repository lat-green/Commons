package com.greentree.common.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public final class RemoveBackIterable<E> implements SizedIterable<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<E> source;
	
	public RemoveBackIterable(Iterable<E> iterable) {
		this.source = iterable;
	}
	
	@Override
	public Spliterator<E> spliterator() {
		final var size = IteratorUtil.size(source);
		return StreamSupport.stream(source.spliterator(), false).limit(size - 1).spliterator();
	}
	
	@Override
	public Iterator<E> iterator() {
		return new RemoveBackIterator<>(source.iterator());
	}
	
	@Override
	public int size() {
		return IteratorUtil.size(source) - 1;
	}
	
}
