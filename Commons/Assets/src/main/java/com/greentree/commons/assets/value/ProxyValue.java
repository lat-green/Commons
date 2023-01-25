package com.greentree.commons.assets.value;

import com.greentree.commons.action.observable.ObjectObservable;


public abstract class ProxyValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	protected final Value<T> source;
	
	public ProxyValue(Value<T> source) {
		this.source = source;
	}
	
	@Override
	public void close() {
		source.close();
	}
	
	@Override
	public T get() {
		return source.get();
	}
	
	@Override
	public boolean isConst() {
		return source.isConst();
	}
	
	@Override
	public boolean isNull() {
		return source.isNull();
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return source.observer();
	}
	
	@Override
	public Value<T> toConst() {
		return source.toConst();
	}
	
	@Override
	public Value<T> toLazy() {
		return source.toLazy();
	}
	
	@Override
	public String toString() {
		return "ProxyValue [" + source + "]";
	}
	
}
