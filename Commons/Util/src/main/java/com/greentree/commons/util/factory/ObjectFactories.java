package com.greentree.commons.util.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.classes.info.TypeInfoBuilder;

public final class ObjectFactories {
	
	public static interface InstanceConstructor<T> extends AutoCloseable {
		
		@Override
		void close();
		
		T value();
		
	}
	
	private static final Map<TypeInfo<?>, ObjectFactory<?>> factories = new HashMap<>();
	
	private ObjectFactories() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> ObjectFactory<T> getFactory(TypeInfo<T> type) {
		Objects.requireNonNull(type);
		synchronized(factories) {
			if(factories.containsKey(type))
				return (ObjectFactory<T>) factories.get(type);
			
			final var factoryAnnotation = type.toClass().getAnnotation(Factory.class);
			if(factoryAnnotation == null)
				return new DefaultObjectFactory<>(type);
			
			final var clsFactory = (Class<ObjectFactory<T>>) factoryAnnotation.value();
			try(final var factory = newInstanceConstructor(clsFactory);) {
				factories.put(type, factory.value());
				return factory.value();
			}
		}
	}
	
	public static <T> T newInstance(Class<T> cls) {
		return newInstance(cls, getDefaultProperties());
	}
	
	public static <T> T newInstance(TypeInfo<T> type) {
		return newInstance(type, getDefaultProperties());
	}
	
	public static InstanceProperties getDefaultProperties() {
		return new EmptyInstanceProperties();
	}
	
	private static <T> InstanceConstructor<T> newInstanceConstructor(Class<T> cls) {
		return newInstanceConstructor(cls, getDefaultProperties());
	}
	
	public static <T> T newInstance(Class<T> cls, InstanceProperties properties) {
		return newInstance(TypeInfoBuilder.getTypeInfo(cls), properties);
	}
	
	public static <T> T newInstance(TypeInfo<T> type, InstanceProperties properties) {
		try(final var instance = newInstanceConstructor(type, properties);) {
			return instance.value();
		}
	}
	
	private static <T> InstanceConstructor<T> newInstanceConstructor(Class<T> cls, InstanceProperties properties) {
		return newInstanceConstructor(TypeInfoBuilder.getTypeInfo(cls), properties);
	}
	
	private static <T> InstanceConstructor<T> newInstanceConstructor(TypeInfo<T> type, InstanceProperties properties) {
		final var factory = getFactory(type);
		return factory.newInstance(properties);
	}
	
}
