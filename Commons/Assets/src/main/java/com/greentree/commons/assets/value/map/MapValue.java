package com.greentree.commons.assets.value.map;

import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.mapper.Mapper;

public interface MapValue<T, R> extends Value<R>, Mapper<T, R> {
	
	void set(T value);
	
	@Override
	default Value<R> toLazy() {
		return LazyValue.newValue(this);
	}
	
	default boolean isMutable() {
		return !isConst();
	}
	
	default Value<R> toNotMutable() {
		if(isMutable())
			return toConst();
		return this;
	}
	
	default MapValue<T, R> copy() {
		if(isConst())
			return this;
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void close() {
	}
}
