package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;

public class MergeIterator<T> implements Iterator<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Iterator<? extends T> element;
	
	private final Iterator<? extends Iterator<? extends T>> iterators;
	
	public MergeIterator(Iterable<? extends Iterator<? extends T>> iterators) {
		this(iterators.iterator());
	}
	
	public MergeIterator(Iterator<? extends Iterator<? extends T>> iterators) {
		this.iterators = iterators;
		if(iterators.hasNext())
			element = iterators.next();
		trim();
	}
	
	@SafeVarargs
	public MergeIterator(Iterator<? extends T>... iterators) {
		this(IteratorUtil.iterable(iterators));
	}
	
	@Override
	public boolean hasNext() {
		return element != null;
	}
	
	@Override
	public T next() {
		final var result = element.next();
		trim();
		return result;
	}
	
	private void trim() {
		while(!element.hasNext() && iterators.hasNext())
			element = iterators.next();
		if(!iterators.hasNext() && !element.hasNext())
			element = null;
	}
	
}
