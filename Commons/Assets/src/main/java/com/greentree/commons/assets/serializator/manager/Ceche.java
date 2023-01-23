package com.greentree.commons.assets.serializator.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public final class Ceche<K, T> {
	
	private static final class CachedValue<T> {
		
		private T value;
		
		public T get() {
			return value;
		}
		
		public void set(T value) {
			this.value = value;
		}
		
	}
	
	private final Map<K, CachedValue<T>> cache = new HashMap<>();
	
	public synchronized T get(K key) {
		final var value = cache.get(key);
		if(value != null)
			return value.get();
		return null;
	}
	
	public synchronized boolean has(K key) {
		return get(key) != null;
	}
	
	public synchronized T set(K key, Supplier<T> supplier) {
		var value = cache.get(key);
		if(value == null) {
			value = new CachedValue<>();
			cache.put(key, value);
			try {
				value.set(supplier.get());
			}catch(Exception e) {
				cache.remove(key);
				throw e;
			}
		}
		return value.get();
	}
	
}
