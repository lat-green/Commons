package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public abstract class MapIterable<T, R> implements SizedIterable<R>, Serializable {

	private static final long serialVersionUID = 1L;

	private final Iterable<? extends T> source;

	/**
	 * Creates iterable that maps elements.
	 *
	 * @param iter source iterable
	 */
	public MapIterable(Iterable<? extends T> iter) {
		this.source = iter;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		MapIterable<?, ?> other = (MapIterable<?, ?>) obj;
		return Objects.equals(source, other.source);
	}

	@Override
	public int hashCode() {
		return Objects.hash(source);
	}

	/**
	 * Returns iterator that maps elements.
	 *
	 * @return mapping iterator
	 */
	@Override
	public Iterator<R> iterator() {
		return new MapIterableMapIterator();
	}

	/**
	 * Returns spliterator that maps elements.
	 *
	 * @return mapping spliterator
	 */
	@Override
	public Spliterator<R> spliterator() {
		final var s = source.spliterator();
		return StreamSupport.stream(s, false).map(x->func(x)).spliterator();
	}

	/**
	 * Returns size of source.
	 *
	 * @return element count
	 */
	@Override
	public int size() {
		return IteratorUtil.size(source);
	}

	@Override
	public String toString() {
		return "MapIterable [iter=" + source + "]";
	}

	/**
	 * Maps element to different type. Subclasses must implement.
	 *
	 * @param t element to map
	 * @return mapped element
	 */
	protected abstract R func(T t);

	private final class MapIterableMapIterator extends MapIterator<T, R> {

		private static final long serialVersionUID = 1L;

		public MapIterableMapIterator() {
			super(source.iterator());
		}

		@Override
		protected R func(T t) {
			return MapIterable.this.func(t);
		}

	}

}
