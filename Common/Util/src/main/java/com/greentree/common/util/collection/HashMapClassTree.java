package com.greentree.common.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Deprecated
/** @author Arseny Latyshev */
public final class HashMapClassTree<E> extends ClassTree<E> {
	
	private static final long serialVersionUID = 1L;
	private final Map<Class<?>, List<?>> map;
	
	public HashMapClassTree() {
		this.map = new HashMap<>();
	}
	
	public HashMapClassTree(final HashMapClassTree<? extends E> classTree) {
		this.map = new HashMap<>(classTree.map);
	}
	
	public HashMapClassTree(final Iterable<E> collection) {
		this();
		this.addAll(collection);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean add(final E object, final Class<?> clazz) {
		if(clazz == null)
			throw new NullPointerException("clazz is null");
		List<E> list = (List<E>) this.map.get(clazz);
		if(list == null) {
			list = new CopyOnWriteArrayList<>();
			map.put(clazz, list);
		}
		boolean flag = list.add(object);
		final Class<?> superClazz = clazz.getSuperclass();
		if(superClazz != null)
			this.add(object, superClazz);
		for(final Class<?> interfase : clazz.getInterfaces())
			this.add(object, interfase);
		return flag;
	}
	
	@Override
	public void clear() {
		this.map.clear();
	}
	
	@Override
	public boolean contains(final Object obj) {
		return this.getAll(Object.class).contains(obj);
	}
	
	@Override
	public boolean containsClass(final Class<? extends E> clazz) {
		return this.map.containsKey(clazz);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(final Class<T> c) {
		if(c == null)
			throw new NullPointerException("class is null");
		final List<T> set = (List<T>) this.map.get(c);
		if(set == null)
			return new ArrayList<>();
		return set;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) this.getAll(Object.class).iterator();
	}
	
	@Override
	public boolean remove(final Object obj) {
		final boolean res = this.contains(obj);
		this.remove(obj.getClass(), obj);
		return res;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean removeAll(final Collection<?> collection) {
		return this.removeAll0((Collection<? extends E>) collection);
	}
	
	@Override
	public <T> T removeOne(final Class<T> c) {
		final T a = this.getOne(c);
		this.remove(a);
		return a;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return getAll(Object.class).retainAll(c);
	}
	
	@Override
	public int size() {
		return this.getAll(Object.class).size();
	}
	
	@Override
	public Object[] toArray() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		return (T[]) getAll(Object.class).toArray();
	}
	
	@Override
	public String toString() {
		return "ClassList " + this.getAll(Object.class);
	}
	
	private void remove(final Class<?> clazz, final Object obj) {
		if(clazz == null)
			return;
		var set = this.map.get(clazz);
		if(set == null)
			return;
		set.remove(obj);
		if(set.isEmpty())
			map.remove(clazz);
		this.remove(clazz.getSuperclass(), obj);
		for(final Class<?> interfase : clazz.getInterfaces())
			this.remove(interfase, obj);
	}
	
	private boolean removeAll0(final Collection<? extends E> collection) {
		boolean t = false;
		for(final E e : collection)
			if(this.remove(e))
				t = true;
		return t;
	}
}
