package com.greentree.common.util.iterator;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class EmptyIterator<E> implements SizedIterator<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private static final EmptyIterator<?> INSTANCE = new EmptyIterator<>(); 
	
	protected Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private EmptyIterator() {
	}
	
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public E next() {
		throw new NoSuchElementException();
	}

	@SuppressWarnings("unchecked")
	public static <T> EmptyIterator<T> instance() {
		return (EmptyIterator<T>) INSTANCE;
	}

	@Override
	public int size() {
		return 0;
	}



}
