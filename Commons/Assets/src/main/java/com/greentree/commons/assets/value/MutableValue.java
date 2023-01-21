package com.greentree.commons.assets.value;

import java.util.Objects;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.action.observer.object.EventAction;
import com.greentree.commons.assets.value.map.SerializableMapValue;

public final class MutableValue<T> extends AbstractValue<T> implements SerializableMapValue<T, T> {
	
	private static final long serialVersionUID = 1L;
	
	private T value;
	private final EventAction<T> action = new EventAction<>();
	
	public MutableValue() {
	}
	
	public MutableValue(T value) {
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
		if(!Objects.equals(value, this.value)) {
			this.value = value;
			event();
		}
	}
	
	@Override
	public String toString() {
		return "Mutable [" + value + "]";
	}
	
}
