package com.greentree.common.util.collection;

import java.util.HashMap;
import java.util.Map;

public abstract class AutoGenerateMap<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = 1L;

	public AutoGenerateMap() {
	}

	public AutoGenerateMap(int initialCapacity) {
		super(initialCapacity);
	}

	public AutoGenerateMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public AutoGenerateMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		V res = super.get(key);
		if(res != null)return res;
		K k = (K) key;
		res = generate(k);
		super.put(k, res);
		generateNew(res);
		return res;
	}

	@Override
	public V put(K k, V v) {
		V res = super.put(k, v);
		if(res != null) return res;
		return generate(k);
	}
	@SuppressWarnings("unchecked")
	@Override
	public V remove(Object key) {
		V res = super.remove(key);
		if(res != null)return res;
		return generate((K) key);
	}

	protected abstract V generate(K k);

	protected void generateNew(V v) {
	}

}
