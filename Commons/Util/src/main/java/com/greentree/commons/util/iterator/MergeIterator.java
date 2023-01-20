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

	@SafeVarargs
	public MergeIterator(Iterator<? extends T>...iterators) {
		this(IteratorUtil.iterable(iterators));
	}
	public MergeIterator(Iterator<? extends Iterator<? extends T>> iterators) {
		this.iterators = iterators;
		trim();
	}

	@Override
	public boolean hasNext() {
		return element != null && element.hasNext();
	}

	@Override
	public T next() {
		trim();
		if(!hasNext()) return null;
		return element.next();
	}

	private void trim() {
		while(element == null)
			if(iterators.hasNext())
				element = iterators.next();
			else
				return;
		if(!element.hasNext()) {
			if(iterators.hasNext())
				element = iterators.next();
			else
				return;
			trim();
		}
	}

}
