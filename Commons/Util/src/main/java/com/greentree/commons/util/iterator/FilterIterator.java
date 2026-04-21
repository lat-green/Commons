package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class FilterIterator<E> implements Iterator<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private final Predicate<? super E> filter;
	private boolean hasNext;

	private final Iterator<? extends E> iterator;

	private E next;

	/**
	 * Creates iterator that filters elements using the given predicate.
	 *
	 * @param iterator source iterator
	 * @param filter predicate to test elements
	 */
	public FilterIterator(Iterator<? extends E> iterator, Predicate<? super E> filter) {
		this.filter = filter;
		this.iterator = iterator;
		hasNext = true;
		next();
	}

	/**
	 * Returns true if next element passes the filter.
	 *
	 * @return true if has more elements
	 */
	@Override
	public boolean hasNext() {
		return hasNext;
	}

	/**
	 * Returns next element that passes the filter.
	 *
	 * @return next element
	 */
	@Override
	public E next() {
		if(!hasNext()) throw new NoSuchElementException();
		try {
			return next;
		}finally {
			while(true) {
				if(!iterator.hasNext()) {
					next = null;
					hasNext = false;
					break;
				}
				next = iterator.next();
				if(filter.test(next)) {
					hasNext = true;
					break;
				}
			}
		}
	}



}
