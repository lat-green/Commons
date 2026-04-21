package com.greentree.commons.util.iterator;

import java.util.Iterator;

public interface SizedIterator<T> extends Iterator<T> {

	/**
	 * Returns number of remaining elements.
	 *
	 * @return remaining elements count
	 */
	int size();



}
