package com.greentree.common.util.collection;

import java.util.AbstractCollection;
import java.util.Iterator;


public class InfinityCollection<E> extends AbstractCollection<E> {

	@Override
	public boolean add(E e) {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return true;
	}

	@Override
	public Iterator<E> iterator() {
		return null;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}

}
