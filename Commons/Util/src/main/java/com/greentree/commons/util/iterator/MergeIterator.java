package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;

public class MergeIterator<T> implements Iterator<T>, Serializable {

	private static final long serialVersionUID = 1L;

	private Iterator<? extends T> element;

	private final Iterator<? extends Iterator<? extends T>> iterators;

	/**
	 * Creates iterator that merges multiple iterables.
	 *
	 * @param iterators iterables to merge
	 */
	public MergeIterator(Iterable<? extends Iterator<? extends T>> iterators) {
		this(iterators.iterator());
	}

	/**
	 * Creates iterator that merges multiple iterators.
	 *
	 * @param iterators iterators to merge
	 */
	public MergeIterator(Iterator<? extends Iterator<? extends T>> iterators) {
		this.iterators = iterators;
		if(iterators.hasNext())
			element = iterators.next();
		trim();
	}

	/**
	 * Creates iterator that merges multiple iterators.
	 *
	 * @param iterators iterators to merge
	 */
	@SafeVarargs
	public MergeIterator(Iterator<? extends T>... iterators) {
		this(IteratorUtil.iterable(iterators));
	}

	/**
	 * Returns true if more elements remain.
	 *
	 * @return true if has more elements
	 */
	@Override
	public boolean hasNext() {
		return element != null;
	}

	/**
	 * Returns next element from any iterator.
	 *
	 * @return next element
	 */
	@Override
	public T next() {
		final var result = element.next();
		trim();
		return result;
	}

	/**
	 * Advances to next iterator with elements.
	 */
	private void trim() {
		while(!element.hasNext() && iterators.hasNext())
			element = iterators.next();
		if(!iterators.hasNext() && !element.hasNext())
			element = null;
	}

}
