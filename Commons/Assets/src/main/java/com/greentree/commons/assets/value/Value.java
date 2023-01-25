package com.greentree.commons.assets.value;

import java.io.Serializable;

import com.greentree.commons.action.observable.ObjectObservable;
import com.greentree.commons.assets.value.map.LazyValue;

public interface Value<T> extends AutoCloseable, Serializable {
	
	
	@Override
	default void close() {
	}
	
	default boolean isConst() {
		return false;
	}
	
	default boolean isNull() {
		return get() == null;
	}
	
	T get();
	
	ObjectObservable<T> observer();
	
	default Value<T> toConst() {
		return ConstValue.newValue(get());
	}
	
	default Value<T> toLazy() {
		return ConstWrappedValue.newValue(this, LazyValue.newValue());
	}
	
	default Value<T> copy() {
		if(isConst())
			return this;
		throw new UnsupportedOperationException();
	}
	
}
