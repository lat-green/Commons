package com.greentree.commons.util.iterator;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.StreamSupport;


public class MergeIterable<T> implements SizedIterable<T>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Iterable<? extends Iterable<? extends T>> iters;
	
	public MergeIterable(Iterable<? extends Iterable<? extends T>> iters) {
		this.iters = iters;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if((obj == null) || (getClass() != obj.getClass()))
			return false;
		MergeIterable<?> other = (MergeIterable<?>) obj;
		return Objects.equals(iters, other.iters);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(iters);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Spliterator<T> spliterator() {
		return (Spliterator<T>) StreamSupport.stream(iters.spliterator(), true)
				.flatMap(i->StreamSupport.stream(i.spliterator(), true)).spliterator();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new MergeIterator<>(IteratorUtil.map(iters, i->i.iterator()));
	}
	
	@Override
	public String toString() {
		return "MergeIterable [iters=" + iters + "]";
	}
	
	@Override
	public int size() {
		int sum = 0;
		for(var i : iters)
			sum += IteratorUtil.size(i);
		return sum;
	}
	
}
