package com.greentree.common.util.iterator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class FilterIterable<T> implements SizedIterable<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Predicate<? super T> filter;
	private final Iterable<T> source;
	
	public FilterIterable(Iterable<T> source, Predicate<? super T> filter) {
		this.filter = filter;
		this.source = source;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (getClass() != obj.getClass()))
			return false;
		FilterIterable<?> other = (FilterIterable<?>) obj;
		return Objects.equals(filter, other.filter) && Objects.equals(source, other.source);
	}
	
	@Override
	public Spliterator<T> spliterator() {
		return StreamSupport.stream(source.spliterator(), false).filter(filter).spliterator();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(filter, source);
	}
	
	@Override
	public FilterIterator<T> iterator() {
		return new FilterIterator<>(source.iterator(), filter);
	}
	
	@Override
	public int size() {
		return IteratorUtil.size(source);
	}
	
	@Override
	public String toString() {
		return "FilterIterable [filter=" + filter + ", iterable=" + source + "]";
	}
	
}
