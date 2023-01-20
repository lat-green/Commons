package com.greentree.commons.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Deprecated
/** @author Arseny Latyshev */
public class OneExtendClassTree<E> extends ClassTree<E> {
	
	private static final long serialVersionUID = 1L;
	private final Collection<E> list;
	
	private final Map<Class<?>, E> map;
	private final Class<?> root;
	
	public OneExtendClassTree(final Class<E> root) {
		this(10, root);
	}
	
	public OneExtendClassTree(final int size, final Class<E> root) {
		map = new HashMap<>(size);
		list = new ArrayList<>(size);
		this.root = root;
	}
	
	@Override
	public void clear() {
		this.map.clear();
		this.list.clear();
	}
	
	@Override
	public boolean contains(final Object obj) {
		return list.contains(obj);
	}
	
	@Override
	public boolean containsClass(final Class<? extends E> clazz) {
		return map.containsKey(clazz);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		OneExtendClassTree<?> other = (OneExtendClassTree<?>) obj;
		return Objects.equals(list, other.list) && Objects.equals(map, other.map)
				&& Objects.equals(root, other.root);
	}
	
	public final <T> T get(final Class<T> c) {
		return getOne(c);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(final Class<T> c) {
		final List<T> res = new ArrayList<>();
		if(c == root) {
			for(E a : list)
				res.add((T) a);
			return res;
		}
		res.add(getOne(c));
		return res;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getOne(final Class<T> c) {
		if(c == null)
			throw new NullPointerException("class is null");
		return (T) map.get(c);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(list, map, root);
	}
	
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}
	
	public <T> T remove(final Class<T> clazz) {
		T r = get(clazz);
		remove(r);
		return r;
	}
	
	@Override
	public boolean remove(final Object obj) {
		if(list.remove(obj)) {
			remove0(obj.getClass());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeAll(final Collection<?> collection) {
		boolean t = false;
		for(final var e : collection)
			if(this.remove(e))
				t = true;
		return t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeOne(final Class<T> c) {
		final T a = (T) map.remove(c);
		if(a == null)
			return null;
		list.remove(a);
		return a;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}
	
	@Override
	public int size() {
		return list.size();
	}
	
	@Override
	public Object[] toArray() {
		return list.toArray();
	}
	
	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}
	
	@Override
	public String toString() {
		return "ClassList " + list;
	}
	
	private <T> void remove0(final Class<T> clazz) {
		map.remove(clazz);
		if(clazz == root)
			return;
		remove0(clazz.getSuperclass());
	}
	
	@Override
	protected final boolean add(final E object, final Class<?> clazz) {
		if(clazz == null)
			throw new NullPointerException("clazz is null");
		if(clazz == root)
			list.add(object);
		else
			if(!isIgnore(clazz)) {
				if(map.containsKey(clazz))
					if(object == map.get(clazz))
						return true;
					else
						throw new IllegalArgumentException(object + " wants to take "
								+ clazz.getName() + " already taken " + map.get(clazz));
				map.put(clazz, object);
				this.add(object, clazz.getSuperclass());
			}
		return true;
	}
	
	protected boolean isIgnore(Class<?> clazz) {
		return false;
	}
	
	
}
