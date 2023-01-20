package com.greentree.commons.util.collection;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
public class SoftHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final HashMap<K, SoftReference<V>> map = new HashMap<>();
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		return map.entrySet().parallelStream()
				.map(e->new SimpleEntry<>(e.getKey(), e.getValue().get()))
				.collect(Collectors.toSet());
	}
	
	@Override
	public V get(Object key) {
		var sr = map.get(key);
		if(sr == null)
			return null;
		return sr.get();
	}
	
	@Override
	public V put(K key, V value) {
		var sr = map.put(key, new SoftReference<>(value));
		if(sr == null)
			return null;
		return sr.get();
	}
	
	@Override
	public V remove(Object key) {
		var sr = map.remove(key);
		if(sr == null)
			return null;
		return sr.get();
	}
	
}
