package com.greentree.commons.assets.value.mapper;

import com.greentree.commons.assets.value.store.Store;

public abstract class AbstractMapper<T, R> implements Mapper<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final Store<T, R> store;
	
	public AbstractMapper(Store<T, R> store) {
		this.store = store;
		init();
	}
	
	@Override
	public final void close() {
		store.close();
	}
	
	@Override
	public Mapper<T, R> copy() {
		return copy(store.copy());
	}
	
	@Override
	public final R get() {
		return store.result();
	}
	
	@Override
	public final void set(T source) {
		var value = store.result();
		try {
			value = map0(source, value);
		}catch(RuntimeException e) {
			e.printStackTrace();
			return;
		}
		store.source(source);
		store.result(value);
	}
	
	private void init() {
		store.init(this::map);
	}
	
	private R map0(T source, R oldValue) {
		if(oldValue == null)
			return map(source);
		else
			return map(source, oldValue);
	}
	
	@java.io.Serial
	private void readObject(java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException {
		s.defaultReadObject();
		init();
	}
	
	protected abstract AbstractMapper<T, R> copy(Store<T, R> store);
	
	protected abstract R map(T source);
	
	protected abstract R map(T source, R oldValue);
	
}
