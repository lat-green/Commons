package com.greentree.common.util.collection;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.function.Predicate;


public final class PredicateCollection<E> extends AbstractCollection<E> {
	
	private Predicate<Object> contains;
	
	public PredicateCollection(Predicate<Object> contains) {
		this.contains = contains;
	}
	
	@Override
	public boolean add(E e) {
		if(contains(e))
			return false;
		contains = x->x.equals(e) || contains.test(e);
		return true;
	}
	
	@Override
	public boolean contains(Object o) {
		return contains.test(o);
	}
	
	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean remove(Object e) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}
	
}
