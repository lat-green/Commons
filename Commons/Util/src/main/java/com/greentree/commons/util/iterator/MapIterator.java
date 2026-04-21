package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;


public abstract class MapIterator<T, R> implements Iterator<R>, Serializable {
	private static final long serialVersionUID = 1L;

	private final Iterator<? extends T> iter;

	/**
	 * Creates iterator that maps elements.
	 *
	 * @param iter source iterator
	 */
	public MapIterator(Iterator<? extends T> iter) {
		this.iter = iter;
	}

	@Override
	public final boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		MapIterator<?, ?> other = (MapIterator<?, ?>) obj;
		return Objects.equals(iter, other.iter);
	}

	@Override
	public final int hashCode() {
		return Objects.hash(iter);
	}

	/**
	 * Delegates to source iterator.
	 *
	 * @return true if has more elements
	 */
	@Override
	public final boolean hasNext() {
		return iter.hasNext();
	}

	/**
	 * Maps element to different type. Subclasses must implement.
	 *
	 * @param t element to map
	 * @return mapped element
	 */
	protected abstract R func(T t);

	/**
	 * Returns next mapped element.
	 *
	 * @return next element
	 */
	@Override
	public final R next() {
		final var e = iter.next();
		return func(e);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [iter=" + iter + "]";
	}

}
