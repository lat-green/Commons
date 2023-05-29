package com.greentree.commons.injector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.greentree.commons.reflection.ClassUtil;

public final class InjectionContainerImpl implements ConfigInjectionContainer {
	
	
	private final Collection<Source<Object>> beans = new ArrayList<>();
	
	@Override
	public <T> Optional<T> get(Class<T> cls) {
		var all = getAll(cls);
		return all.map(x -> x.value).findAny();
	}
	
	@Override
	public Optional<Object> get(String name) {
		return beans.stream().filter(x -> name.equals(x.name)).map(x -> x.value).findAny();
	}
	
	@Override
	public <T> Optional<T> get(String name, Class<T> cls) {
		var all = getAll(cls);
		return all.filter(x -> name.equals(x.name)).map(x -> x.value).findAny();
	}
	
	@Override
	public void put(Object value) {
		beans.add(new Source<>(value));
	}
	
	@Override
	public void put(String name, Object value) {
		beans.add(new Source<>(name, value));
	}
	
	@Override
	public void putPriority(Object value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void putPriority(String name, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> Stream<Source<T>> getAll(Class<T> cls) {
		var type = ClassUtil.getNotPrimitive(cls);
		return beans.stream().filter(x -> type.isInstance(x.value)).map(x -> (Source<T>) x);
	}
	
	private record Source<T>(String name, T value) {
		
		public Source {
			Objects.requireNonNull(name);
			Objects.requireNonNull(value);
		}
		
		public Source(T value) {
			this(defaultName(value), value);
		}
		
		public static String defaultName(Object obj) {
			return firstToLowerCase(obj.getClass().getSimpleName());
		}
		
		public static String firstToLowerCase(String str) {
			var first = str.substring(0, 1);
			var other = str.substring(1);
			first = first.toLowerCase();
			return first + other;
		}
		
	}
}
