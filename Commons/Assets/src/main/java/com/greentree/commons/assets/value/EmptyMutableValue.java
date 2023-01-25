package com.greentree.commons.assets.value;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.assets.value.map.MapValue;

public final class EmptyMutableValue<T> implements MapValue<T, T> {
	
	private static final long serialVersionUID = 1L;
	
	private transient T value;
	private final EventAction<T> action = new EventAction<>();
	
	public EmptyMutableValue() {
	}
	
	@Override
	public void close() {
		value = null;
	}
	
	public EmptyMutableValue(T value) {
		this.value = value;
	}
	
	public void event() {
		action.event(value);
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return action;
	}
	
	@Override
	public void set(T value) {
		this.value = value;
		event();
	}
	
	@Override
	public String toString() {
		return "Mutable [" + value + "]";
	}
	
}
