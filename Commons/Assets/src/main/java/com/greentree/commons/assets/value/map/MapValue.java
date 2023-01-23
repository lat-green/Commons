package com.greentree.commons.assets.value.map;

import com.greentree.commons.assets.value.Value;

public interface MapValue<T, R> extends Value<R> {
	
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
	
}
