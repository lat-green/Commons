package com.greentree.commons.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Deprecated
/** @author Arseny Latyshev */
public class MultyExtendClassTree extends ClassTree<Object> {
	
	private static final long serialVersionUID = 1L;
	
	private final Collection<Object> list;
	
	private final Map<Class<?>, Collection<Object>> map;
	
	public MultyExtendClassTree() {
		map = new FunctionAutoGenerateMap<>(c->new HashSet<>());
		list = new ArrayList<>();
	}
	
	@Override
	public void clear() {
		map.clear();
		list.clear();
	}
	
	@Override
	public boolean contains(final Object obj) {
		return list.contains(obj);
	}
	
	@Override
	public boolean containsClass(final Class<?> clazz) {
		return !map.get(clazz).isEmpty();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		MultyExtendClassTree other = (MultyExtendClassTree) obj;
		return Objects.equals(list, other.list);
	}
	
	public final <T> T get(final Class<T> c) {
		return getOne(c);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getAll(final Class<T> c) {
		return (List<T>) new ArrayList<>(map.get(c));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public final <T> T getOne(final Class<T> c) {
		if(c == null)
			throw new NullPointerException("class is null");
		for(var t : map.get(c))
			return (T) t;
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(map);
	}
	
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	@Override
	public Iterator<Object> iterator() {
		return list.iterator();
	}
	
	@SuppressWarnings("unchecked")
	public <T> Iterator<T> iterator(Class<T> clazz) {
		return (Iterator<T>) map.get(clazz).iterator();
	}
	
	@Override
	public boolean remove(final Object object) {
		if(list.remove(object)) {
			map.values().parallelStream().forEach(c->c.remove(object));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean removeAll(final Collection<?> collection) {
		boolean t = false;
		for(final var e : collection)
			if(remove(e))
				t = true;
		return t;
	}
	
	@Override
	public <T> T removeOne(final Class<T> c) {
		T t = getOne(c);
		remove(t);
		return t;
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
	
	@Override
	protected final boolean add(final Object object, final Class<?> clazz) {
		if(map.get(clazz).add(object)) {
			
			if(clazz != Object.class) {
				add(object, clazz.getSuperclass());
				for(var i : clazz.getInterfaces())
					addI(object, i);
			}else
				list.add(object);
			
			return true;
		}
		return false;
	}
	
	protected final void addI(final Object object, final Class<?> clazz) {
		if(map.get(clazz).add(object))
			for(var i : clazz.getInterfaces())
				addI(object, i);
	}
	
}
