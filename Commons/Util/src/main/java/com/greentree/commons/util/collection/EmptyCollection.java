package com.greentree.commons.util.collection;

import java.util.AbstractCollection;
import java.util.Iterator;

import com.greentree.commons.util.iterator.EmptyIterator;


public class EmptyCollection<E> extends AbstractCollection<E> {

	/**
	 * Always returns false as this collection cannot contain elements.
	 *
	 * @param e element to add
	 * @return false
	 */
	@Override
	public boolean add(E e) {
		return false;
	}

	/**
	 * Returns an empty iterator.
	 *
	 * @return empty iterator
	 */
	@Override
	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	/**
	 * Always returns false as this collection cannot contain elements.
	 *
	 * @param o element to remove
	 * @return false
	 */
	@Override
	public boolean remove(Object o) {
		return false;
	}

	/**
	 * Returns 0 as this collection is always empty.
	 *
	 * @return 0
	 */
	@Override
	public int size() {
		return 0;
	}

}
