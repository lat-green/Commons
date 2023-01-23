package com.greentree.commons.assets.value.map;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.MutableValue;


public final class LazyValue<T, R> implements MapValue<T, R> {
	
	private static final long serialVersionUID = 1L;
	
	private final MapValue<T, R> value;
	
	private transient boolean setFlag;
	private transient T nextValue;
	
	private LazyValue(MapValue<T, R> value) {
		this.value = value;
	}
	
	public static <T> LazyValue<T, T> newValue() {
		return new LazyValue<>(new MutableValue<>());
	}
	
	public static <T, R> LazyValue<T, R> newValue(MapValue<T, R> value) {
		return new LazyValue<>(value);
	}
	
	@Override
	public void close() {
		value.close();
	}
	
	@Override
	public R get() {
		synchronized(this) {
			if(setFlag) {
				setFlag = false;
				value.set(nextValue);
				nextValue = null;
			}
			return value.get();
		}
	}
	
	@Override
	public boolean isConst() {
		return value.isConst();
	}
	
	@Override
	public ObjectObservable<R> observer() {
		return value.observer();
	}
	
	@Override
	public void set(T value) {
		synchronized(this) {
			setFlag = true;
			nextValue = value;
		}
	}
	
	@Override
	public String toString() {
		if(setFlag)
			return "Lazy [nextValue=" + nextValue + ", " + value + "]";
		return "Lazy [" + value + "]";
	}
	
}
