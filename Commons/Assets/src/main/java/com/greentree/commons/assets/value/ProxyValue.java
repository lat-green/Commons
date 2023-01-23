package com.greentree.commons.assets.value;

import com.greentree.commons.action.observable.ObjectObservable;


public class ProxyValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	private final Value<T> source;
	
	public ProxyValue(Value<T> source) {
		this.source = source;
	}
	
	@Override
	public T get() {
		return source.get();
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return source.observer();
	}
	
	@Override
	public boolean isConst() {
		return source.isConst();
	}
	
	@Override
	public void close() {
		source.close();
	}
	
	@Override
	public boolean isNull() {
		return source.isNull();
	}
	
	@Override
	public Value<T> toConst() {
		return source.toConst();
	}
	
	@Override
	public Value<T> toLazy() {
		return source.toLazy();
	}
	
}
