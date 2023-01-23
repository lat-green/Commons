package com.greentree.commons.assets.value;

import com.greentree.commons.action.observable.ObjectObservable;

public final class ConstValue<T> extends AbstractValue<T> implements SerializableValue<T> {
	
	private static final long serialVersionUID = 1L;
	
	private final T value;
	
	public static <T> Value<T> newValue(T value) {
		if(value == null)
			return NullValue.instance();
		return new ConstValue<>(value);
	}
	
	private ConstValue(T value) {
		this.value = value;
	}
	
	@Override
	public T get() {
		return value;
	}
	
	@Override
	public ObjectObservable<T> observer() {
		return ObjectObservable.getNull();
	}
	
	@Override
	public String toString() {
		return "ConstValue [" + value + "]";
	}
	
	@Override
	public boolean isNull() {
		return false;
	}
	
	@Override
	public boolean isConst() {
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
