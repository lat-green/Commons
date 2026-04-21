package com.greentree.commons.util.iterator;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.NoSuchElementException;

public final class EmptyIterator<E> implements SizedIterator<E>, Serializable {
	private static final long serialVersionUID = 1L;

	private static final EmptyIterator<?> INSTANCE = new EmptyIterator<>();

	/**
	 * Resolves to the singleton instance during deserialization.
	 *
	 * @return singleton instance
	 * @throws ObjectStreamException if resolution fails
	 */
	protected Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}

	private EmptyIterator() {
	}

	/**
	 * Always returns false.
	 *
	 * @return false
	 */
	@Override
	public boolean hasNext() {
		return false;
	}

	/**
	 * Throws NoSuchElementException as this iterator is always empty.
	 *
	 * @return never returns
	 * @throws NoSuchElementException always
	 */
	@Override
	public E next() {
		throw new NoSuchElementException();
	}

	/**
	 * Returns the singleton empty iterator instance.
	 *
	 * @param <T> type of iterator elements
	 * @return singleton instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> EmptyIterator<T> instance() {
		return (EmptyIterator<T>) INSTANCE;
	}

	/**
	 * Returns 0 as this iterator is always empty.
	 *
	 * @return 0
	 */
	@Override
	public int size() {
		return 0;
	}



}
