package com.greentree.commons.util.collection;

import java.util.AbstractCollection;
import java.util.Iterator;

import com.greentree.commons.util.iterator.EmptyIterator;


public class EmptyCollection<E> extends AbstractCollection<E> {

	@Override
	public boolean add(E e) {
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return EmptyIterator.instance();
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public int size() {
		return 0;
	}

}
