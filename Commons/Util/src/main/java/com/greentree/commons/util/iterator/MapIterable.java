package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public abstract class MapIterable<T, R> implements SizedIterable<R>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends T> source;
	
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
	
	@Override
	public Iterator<R> iterator() {
		return new MapIterableMapIterator();
	}
	
	@Override
	public Spliterator<R> spliterator() {
		final var s = source.spliterator();
		return StreamSupport.stream(s, false).map(x->func(x)).spliterator();
	}
	
	@Override
	public int size() {
		return IteratorUtil.size(source);
	}
	
	@Override
	public String toString() {
		return "MapIterable [iter=" + source + "]";
	}
	
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
