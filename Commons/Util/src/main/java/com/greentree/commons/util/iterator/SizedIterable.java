package com.greentree.commons.util.iterator;

import java.util.Spliterator;
import java.util.Spliterators;

public interface SizedIterable<T> extends Iterable<T> {
	
	int size();
	
	@Override
	default Spliterator<T> spliterator() {
		return Spliterators.spliterator(iterator(), size(), 0);
	}
	
}
