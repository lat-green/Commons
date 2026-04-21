package com.greentree.commons.util.iterator;

import java.util.Spliterator;
import java.util.Spliterators;

public interface SizedIterable<T> extends Iterable<T> {

	/**
	 * Returns number of elements.
	 *
	 * @return element count
	 */
	int size();

	/**
	 * Returns spliterator with known size.
	 *
	 * @return spliterator
	 */
	@Override
	default Spliterator<T> spliterator() {
		return Spliterators.spliterator(iterator(), size(), 0);
	}

}
