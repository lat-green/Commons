package com.greentree.commons.injector.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.greentree.commons.injector.factory.ObjectFactories.InstanceConstructor;
import com.greentree.commons.reflection.info.TypeInfo;

public final class DefaultObjectFactory<T> implements ObjectFactory<T> {
	
	private final TypeInfo<T> type;
	
	public DefaultObjectFactory(TypeInfo<T> type) {
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getMaxConstructor(TypeInfo<T> type, Iterable<? extends String> names) {
		final var cls = type.toClass();
		final var set = new HashSet<>();
		for(var n : names)
			set.add(n);
		Constructor<?> constructor = null;
		A :
		for(Constructor<?> c : cls.getDeclaredConstructors()) {
			for(var p : c.getParameters())
				if(!set.contains(p.getName()))
					continue A;
			if(constructor == null)
				constructor = c;
			if(constructor.getParameterCount() < c.getParameterCount())
				constructor = c;
		}
		return (Constructor<T>) constructor;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> getMinConstructor(TypeInfo<T> type) {
		final var cls = type.toClass();
		Constructor<?> constructor = null;
		for(Constructor<?> c : cls.getDeclaredConstructors()) {
			if(constructor == null)
				constructor = c;
			if(constructor.getParameterCount() > c.getParameterCount())
				constructor = c;
		}
		return (Constructor<T>) constructor;
	}
	
	
	public static <T> T newInstanse(TypeInfo<T> type, InstanceProperties properties) {
		var c = getMaxConstructor(type, properties.names());
		if(c == null)
			c = getMinConstructor(type);
		if(c == null)
			throw new UnsupportedOperationException(type + " not have constructor(maybe abstract)");
		var args = new Object[c.getParameterCount()];
		try(final var a = openAccess(c)) {
			{
				var params = c.getParameters();
				for(var i = 0; i < params.length; i++) {
					var p = params[i];
					var r = properties.get(p.getName()).value();
					args[i] = r;
				}
			}
			return c.newInstance(args);
		}catch(Exception e) {
			throw new UnsupportedOperationException("args: " + Arrays.toString(args) + " constructor: "
					+ Arrays.asList(c.getParameters()).stream().map(Parameter::getName).collect(Collectors.toList()) + " type:" + type, e);
		}
	}
	
	private static AutoCloseable openAccess(Constructor<?> constructor) {
		final var access = constructor.canAccess(null);
		if(!constructor.trySetAccessible())
			constructor.setAccessible(true);
		return ()->constructor.setAccessible(access);
	}
	
	@Override
	public InstanceConstructor<T> newInstance(InstanceProperties properties) {
		return new InstanceConstructor<>() {
			
			private T value;
			
			@Override
			public void close() {
				
			}
			
			@Override
			public T value() {
				if(value != null)
					return value;
				value = newInstanse(type, properties);
				return value;
			}
		};
	}
	
	@Override
	public String toString() {
		return "DefaultObjectFactory [" + type + "]";
	}
	
}
