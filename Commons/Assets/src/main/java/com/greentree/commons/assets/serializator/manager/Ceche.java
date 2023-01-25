package com.greentree.commons.assets.serializator.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public final class Ceche<K, T> {
	
	
	private final Map<K, CachedValue<T>> cache = new HashMap<>();
	
	public T get(K key) {
		final var value = cache.get(key);
		if(value != null)
			return value.get();
		return null;
	}
	
	public boolean has(K key) {
		return cache.containsKey(key);
	}
	
	public T set(K key, Function<Context, T> supplier) {
		var value = cache.get(key);
		if(value == null) {
			final var v = supplier.apply(closer(key));
			value = new CachedValue<>(v);
			cache.put(key, value);
		}
		value.use();
		return value.get();
	}
	
	private Context closer(K key) {
		return new Context() {
			
			@Override
			public boolean remove() {
				if(cache.get(key).remove()) {
					cache.remove(key);
					return true;
				}
				return false;
			}
			
			@Override
			public void use() {
				cache.get(key).use();
			}
		};
	}
	
	public interface Context {
		
		boolean remove();
		void use();
		
	}
	
	private static final class CachedValue<T> {
		
		private final T value;
		private int refCount;
		
		public CachedValue(T value) {
			this.value = value;
		}
		
		public T get() {
			return value;
		}
		
		public boolean remove() {
			refCount--;
			return refCount <= 0;
		}
		
		public void use() {
			refCount++;
		}
		
	}
	
}
