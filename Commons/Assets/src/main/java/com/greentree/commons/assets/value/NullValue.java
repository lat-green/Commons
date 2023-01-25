package com.greentree.commons.assets.value;

import java.io.ObjectStreamException;

import com.greentree.commons.action.observable.ObjectObservable;

public final class NullValue<T> implements Value<T> {
	
	private static final long serialVersionUID = 1L;
	
	private static final NullValue<?> INSTANCE = new NullValue<>();
	
	private Object readResolve() throws ObjectStreamException {
		return INSTANCE;
	}
	
	private NullValue() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> NullValue<T> instance() {
		return (NullValue<T>) INSTANCE;
	}
	
	@Override
	public T get() {
		return null;
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return ObjectObservable.getNull();
	}
	
	@Override
	public NullValue<T> copy() {
		return this;
	}
	
	@Override
	public boolean isConst() {
		return true;
	}
	
	@Override
	public boolean isNull() {
		return true;
	}
	
	@Override
	public Value<T> toConst() {
		return this;
	}
	
	@Override
	public Value<T> toLazy() {
		return this;
	}
	
}
