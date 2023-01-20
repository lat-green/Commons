package com.greentree.commons.util.iterator;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Iterator;

public class EmptyIterable<T> implements SizedIterable<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static final EmptyIterable<?> INSTANCE = new EmptyIterable<>();
	
	private EmptyIterable() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> EmptyIterable<T> instance() {
		return (EmptyIterable<T>) INSTANCE;
	}
	
	@Override
	public Iterator<T> iterator() {
		return EmptyIterator.instance();
	}
	
	@Override
	public int size() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "[]";
	}
	
	protected Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
}
