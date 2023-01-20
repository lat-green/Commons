package com.greentree.common.util.collection;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

@Deprecated
/** @author Arseny Latyshev */
public abstract class ClassTree<E> implements Collection<E>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public final boolean add(final E object) {
		return add(object, Objects.requireNonNull(object, "object is null").getClass());
	}
	
	@Override
	public final boolean addAll(Collection<? extends E> c) {
		return addAll((Iterable<? extends E>) c);
	}
	
	public final boolean addAll(final Iterable<? extends E> iterable) {
		if(iterable == null)
			throw new NullPointerException("iterable is null");
		boolean t = false;
		for(final E e : iterable)
			try {
				if(this.add(e))
					t = true;
			}catch(final Exception ex) {
				throw new IllegalArgumentException("iterable contains illegal class " + e, ex);
			}
		return t;
	}
	
	@Override
	public final boolean containsAll(final Collection<?> collection) {
		return containsAll((Iterable<?>) collection);
	}
	
	public final boolean containsAll(final Iterable<?> collection) {
		for(final Object obj : collection)
			if(!contains(obj))
				return false;
		return true;
	}
	
	public abstract boolean containsClass(Class<? extends E> clazz);
	
	public abstract <T> List<T> getAll(Class<T> c);
	
	public <T> T getOne(final Class<T> c) {
		final var all = getAll(c);
		if(all.isEmpty())
			return null;
		return all.get(0);
	}
	
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	@Override
	public abstract boolean remove(Object e);
	
	public abstract <T> T removeOne(Class<T> c);
	protected abstract boolean add(E e, Class<?> clazz);
	
	
}
